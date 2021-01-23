package cn.bugio.spring.mini.context;

import cn.bugio.spring.demo.Test;
import cn.bugio.spring.mini.annotation.*;
import cn.bugio.spring.mini.bean.*;
import cn.bugio.spring.mini.constant.DispatchRequest;
import cn.bugio.spring.mini.constant.ScopeEnum;
import cn.bugio.spring.mini.controller.MappingDefinition;
import cn.bugio.spring.mini.exception.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.net.URL;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Vincent Vic
 * @version 1.0
 * @Description Spring Mini Appliction Context
 * @since 2021/1/17 20:12
 */
public class SpringMiniApplictionContext {
    //获取配置类class
    private Class configClass;
    //BeanDefinition Map
    private Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap();
    /**
     * MappingDefinition Map
     * key:path
     * value: MappingDefinition
     */
    private Map<String, Method> handleMapping = new ConcurrentHashMap();
    //SingletonBean Map 单例池
    private Map<String, Object> singletonBeanMap = new ConcurrentHashMap();
    //处理器列表
    private List<BeanPostProcessor> beanPostProcessorList = new ArrayList<>();
    public SpringMiniApplictionContext(Class<?> configClass) {
        this.configClass = configClass;

        //扫描得到Class IOC
        List<Class> classList = scan(configClass);

        /**
         * 初始化Bean IOC
         */
        doInitBeanDefinition(classList);

        //基于class创建单例Bean IOC
        doInstanceSingletonBean();

        /**
         * 扫描接口 MVC
         */
        doInitHandleMapping();
        
    }

    private void doInitHandleMapping() {

        for (Map.Entry<String, BeanDefinition> beanDefinitionEntry : beanDefinitionMap.entrySet()) {
            Class beanClass = beanDefinitionEntry.getValue().getBeanClass();
            if (!beanClass.isAnnotationPresent(Controller.class)) {
                continue;
            }

            String[] mappingPath;
            if (beanClass.isAnnotationPresent(RequestMapping.class)){
                RequestMapping requestMappingAnnotation = (RequestMapping)beanClass.getAnnotation(RequestMapping.class);
                mappingPath = requestMappingAnnotation.value();
            } else {
                mappingPath = new String[]{""};
            }


            //得到所有方法
            for (Method method : beanClass.getMethods()) {

                //如果存在地址绑定
                if (!method.isAnnotationPresent(RequestMapping.class)){
                    continue;
                }

                System.out.println(method.getName());

                RequestMapping requestMappingAnnotation = (RequestMapping)method.getAnnotation(RequestMapping.class);
                String[] tmpMappingPath= requestMappingAnnotation.value();

                for (String s : mappingPath) {
                    for (String st : tmpMappingPath) {
                        String uri = "/"+s+"/"+st;
                        uri = uri.replaceAll("/+", "/");
                        if (handleMapping.containsKey(uri)){
                            throw new RequestMappingException("It already exists :" + uri);
                        }
                        System.out.println(uri);
                        handleMapping.put(uri,method);
                    }
                }

            }

        }
    }

    private void doInitBeanDefinition(List<Class> classList) {
        //过滤， BeanDefinition
        for (Class aClass : classList) {
            //过滤 如果存在组件注解加入Bean
            if (!(aClass.isAnnotationPresent(Component.class) || aClass.isAnnotationPresent(Controller.class) || aClass.isAnnotationPresent(Service.class))){
                continue;
            }

            //BeanPostProcessor
            if (BeanPostProcessor.class.isAssignableFrom(aClass)) {
                try {
                    BeanPostProcessor instance = (BeanPostProcessor) aClass.getDeclaredConstructor().newInstance();
                    beanPostProcessorList.add(instance);
                } catch (Exception e) {
                    throw new ReflexException(e.toString());
                }
            }

            String beanName = null;

            //得到BeanName

            if (aClass.isAnnotationPresent(Component.class) ){
                Component componentAnnotation = (Component)aClass.getAnnotation(Component.class);
                beanName = componentAnnotation.value();
            } else if (aClass.isAnnotationPresent(Controller.class)){
                Controller controllerAnnotation = (Controller)aClass.getAnnotation(Controller.class);
                beanName = controllerAnnotation.value();
            } else if (aClass.isAnnotationPresent(Service.class)){
                Service serviceAnnotation = (Service)aClass.getAnnotation(Service.class);
                beanName = serviceAnnotation.value();
            }

            //如果BeanName不存在，使用默认规则
            if (beanName == null || "".equals(beanName)){
                beanName = BeanUtil.getDefaultBeanName(aClass);
            }
            /**
             * 创建Bean定义，一开始默认单例，非懒加载
             */
            BeanDefinition beanDefinition = new BeanDefinition(aClass,ScopeEnum.SINGLETON,false);
            //获取Scope，如果存在设置
            if (aClass.isAnnotationPresent(Scope.class)){
                Scope scopeAnnotation = (Scope)aClass.getAnnotation(Scope.class);
                if (ScopeEnum.PROTOTYPE.equals(scopeAnnotation.value())){
                    beanDefinition.setScope(scopeAnnotation.value());
                }
            }
            //获取Lazy,如果存在设置
            if (aClass.isAnnotationPresent(Lazy.class)){
                Lazy LazyAnnotation = (Lazy)aClass.getAnnotation(Lazy.class);
                beanDefinition.setIsLazy(true);
            }


            beanDefinitionMap.put(beanName,beanDefinition);
        }
    }

    /**
     * 扫描得到Class
     */
    private List<Class> scan(Class<?> configClass) {

        //校验是否查找注解
        if (!configClass.isAnnotationPresent(ComponentScan.class)) {
            throw new ComponentScanException();
        }
        //获取组件扫描注解
        ComponentScan componentScanAnnotation = configClass.getAnnotation(ComponentScan.class);
        //得到路径  cn.bugio.spring.demo.service
        String path = componentScanAnnotation.value();
        path = path.replace(".","/");

        //得到类加载器
        ClassLoader classLoader = SpringMiniApplictionContext.class.getClassLoader();
        URL url = classLoader.getResource(path);
        File file = new File(url.getFile()); //cn/bugio/spring/demo/service 文件夹

        List<Class> classList = new ArrayList<>();
        //判断是否是文件夹
        if (file.isDirectory()) {
            //存储class文件路径
            List<String> fileList = new ArrayList<>();
            //获取目录
            File[] files = file.listFiles();
            //扫描到所有类class文件
            scanClassFile(fileList, files);

            //转换并加载class
            for (String s : fileList) {
                String temPath = path.replace("/","\\");
                //取得类路径 cn\bugio\spring\demo\service\User
                String srcPath = s.substring(s.indexOf(temPath),s.indexOf(".class"));
                //转换为包名加类名 cn.bugio.spring.demo.service.User
                srcPath = srcPath.replace("\\",".");
                //获取到类
                Class<?> aClass = null;
                try {
                    aClass = classLoader.loadClass(srcPath);
                } catch (ClassNotFoundException e) {
                    throw new ComponentScanException(e.toString());
                }

                classList.add(aClass);

            }
        }
        return classList;
    }

    private void scanClassFile(List<String> fileList, File[] files) {
        for (File f : files) {
            if (f.isDirectory()) {
                scanClassFile(fileList, f.listFiles());
            }

            if (!f.getName().endsWith(".class")){
                continue;
            }
            fileList.add(f.toString());
        }
    }

    private void doInstanceSingletonBean() {
        for (Map.Entry<String, BeanDefinition> beanDefinitionEntry : beanDefinitionMap.entrySet()) {
            String beanName = beanDefinitionEntry.getKey();
            BeanDefinition beanDefinition = beanDefinitionEntry.getValue();

            if (ScopeEnum.SINGLETON.equals(beanDefinition.getScope())){
                //已经存在的情况无需重复
                if (!singletonBeanMap.containsKey(beanName)) {
                    //创建Bean
                    Object bean = doCreateBean(beanName, beanDefinition);
                    //加入单例池
                    singletonBeanMap.put(beanName, bean);
                }

            }
        }
    }

    public Object doCreateBean(String beanName,BeanDefinition beanDefinition){

        //1.实例化
        Class beanClass = beanDefinition.getBeanClass();
        Object bean = null;
        try {
            bean = beanClass.getDeclaredConstructor().newInstance();

            //2.属性填充 DI
            Field[] declaredFields = beanClass.getDeclaredFields();
            //扫描字段
            for (Field declaredField : declaredFields) {
                if (declaredField.isAnnotationPresent(Autowired.class)) {

                    //得到Bean Name
                    String fieldBeanName = declaredField.getName();
                    Autowired autowired = (Autowired)declaredField.getAnnotation(Autowired.class);
                    if (!"".equals(autowired.value().trim())){
                        fieldBeanName = autowired.value();
                    }
                    //得到值
                    Object fieldBean = getBean(fieldBeanName);
                    if (null == fieldBean){
                        throw new BeanException("Null pointer exception from Autowired Bean: Auto assembly could not found bean");
                    }
                    declaredField.setAccessible(true);
                    //赋值
                    declaredField.set(bean,fieldBean);
                }
            }

            //3.Aware
            if (bean instanceof BeanNameAware){
                ((BeanNameAware)bean).setBeanName(beanName);
            }

            //初始化之前
            for (BeanPostProcessor beanPostProcessor : beanPostProcessorList) {
                bean = beanPostProcessor.postProcessBeforeInitialization(bean,beanName);
            }

            //4.InitializingBean
            if (bean instanceof InitializingBean){
                ((InitializingBean)bean).afterPropertiesSet();
            }

            //初始化之后
            for (BeanPostProcessor beanPostProcessor : beanPostProcessorList) {
                bean = beanPostProcessor.postProcessAfterInitialization(bean,beanName);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new BeanException("doCreateBean : " + e.toString());
        }
        return bean;
    }

    public Object getBean(String beanName){

        BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
        if (beanDefinition == null){
            return null;
        }else if (ScopeEnum.PROTOTYPE.equals(beanDefinition.getScope())){
            //创建Bean
            return doCreateBean(beanName, beanDefinition);
        } else if (ScopeEnum.SINGLETON.equals(beanDefinition.getScope())){
            //从单例池中拿到Bean
            Object bean = singletonBeanMap.get(beanName);
            if (null == bean){
                if (singletonBeanMap.containsKey(beanName)){
                    throw new BeanException( "It already exists :" + bean);
                }
                bean = doCreateBean(beanName,beanDefinition);
                if (null == bean){
                    throw new BeanException("BeanException bean is Null pointer");
                }
                singletonBeanMap.put(beanName,bean);
            }

            return bean;

        }
        return null;
    }


    public boolean doDispatch(DispatchRequest dispatchRequest) throws Exception {
        //调用具体方法
        String uri = dispatchRequest.getUri();
        String contextPath = dispatchRequest.getContextPath();
        uri = ("/"+uri).replaceAll(contextPath,"").replaceAll("/+","/");

        //判断是否存在这个服务
        if (!this.handleMapping.containsKey(uri)){

            throw new NotFoundException(uri + " 404 Not Found!");
        }
        //得到path绑定的方法
        Method method = handleMapping.get(uri);


        String beanName = null;
        Class<?> declaringClass = method.getDeclaringClass();
        if (declaringClass.isAnnotationPresent(Controller.class)) {
            Controller annotation = declaringClass.getAnnotation(Controller.class);
            beanName = annotation.value().trim();
        }
        if (null == beanName || "".equals(beanName)){
            beanName = BeanUtil.getDefaultBeanName(declaringClass);
        }


        Map<String, String[]> params = dispatchRequest.getParams();

        //形式参数
        Class<?> [] paramterTypes = method.getParameterTypes();

        //实际参数
        Object[] paramValues = new Object[paramterTypes.length];


        /**
         * 获取参数绑定的值
         */
        String[] paramIndexMapping = new String[paramterTypes.length];
        //得到方法参数中绑定的RequestParam或RequestBody的value
        getParamBindName(method, paramterTypes, paramIndexMapping);

        //设置values
        for (int i = 0; i < paramterTypes.length; i++) {

            Class paramterType  = paramterTypes[i];
//            if (paramterType == HttpServletRequest.class){
//
//            } else if (paramterType == HttpServletResponse.class){
//
//            }

            if (paramterType == String.class){
                paramValues[i] = Arrays.toString(params.get(paramIndexMapping[i]))
                        .replaceAll("\\[|\\]","")
                        .replaceAll("\\s","");

                }

        }





        try {


            method.invoke(getBean(beanName),paramValues);
            //
            //method.invoke(getBean(beanName),new Object[]{params.get("id")[0],params.get("name")[0],params.get("addr")[0]});
        } catch (Exception e) {
            throw new DispatchException(e.toString());
        }

        return true;
    }

    private void getParamBindName(Method method, Class<?>[] paramterTypes, String[] paramIndexMapping) {
        for (int i = 0; i < paramterTypes.length; i++) {
            Annotation[][] pa = method.getParameterAnnotations();
            for (Annotation a : pa[i]) {
                String paramName = null;
                if (a instanceof RequestParam) {
                    paramName = ((RequestParam) a).value();
                } else if (a instanceof RequestBody) {
                    paramName = ((RequestBody) a).value();
                }
                if (!(paramName != null &&!"".equals(paramName.trim()))) {
                    throw new BeanException("RequestParam binding value is Null");
                }
                paramIndexMapping[i] = paramName;
            }
        }
    }
}
