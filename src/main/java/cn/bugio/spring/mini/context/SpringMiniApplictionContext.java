package cn.bugio.spring.mini.context;

import cn.bugio.spring.demo.Test;
import cn.bugio.spring.mini.annotation.*;
import cn.bugio.spring.mini.bean.*;
import cn.bugio.spring.mini.constant.ScopeEnum;
import cn.bugio.spring.mini.exception.BeanException;
import cn.bugio.spring.mini.exception.ComponentScanException;
import cn.bugio.spring.mini.exception.ReflexException;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
    //SingletonBean Map 单例池
    private Map<String, Object> singletonBeanMap = new ConcurrentHashMap();

    private List<BeanPostProcessor> beanPostProcessorList = new ArrayList<>();
    public SpringMiniApplictionContext(Class<?> configClass) {
        this.configClass = configClass;

        //扫描得到Class
        List<Class> classList = scan(configClass);

        //过滤， BeanDefinition
        for (Class aClass : classList) {
            //过滤 如果存在组件注解加入Bean
            if (aClass.isAnnotationPresent(Component.class)) {

                //BeanPostProcessor
                if (BeanPostProcessor.class.isAssignableFrom(aClass)) {
                    try {
                        BeanPostProcessor instance = (BeanPostProcessor) aClass.getDeclaredConstructor().newInstance();
                        beanPostProcessorList.add(instance);
                    } catch (Exception e) {
                        throw new ReflexException(e.toString());
                    }
                }

                //得到BeanName
                Component componentAnnotation = (Component)aClass.getAnnotation(Component.class);
                String beanName = componentAnnotation.value();
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




        //基于class创建单例Bean
        instanceSingletonBean();
        
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
            File[] files = file.listFiles();
            for (File f : files) {

                String temPath = path.replace("/","\\");
                //取得类路径 cn\bugio\spring\demo\service\User
                String srcPath = f.getAbsolutePath().substring(f.getAbsolutePath().indexOf(temPath),f.getAbsolutePath().indexOf(".class"));
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

    private void instanceSingletonBean() {
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

            //2.属性填充
            Field[] declaredFields = beanClass.getDeclaredFields();
            //扫描字段
            for (Field declaredField : declaredFields) {
                if (declaredField.isAnnotationPresent(Autowired.class)) {
                    //得到值
                    Object fieldBean = getBean(declaredField.getName());
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
                bean = doCreateBean(beanName,beanDefinition);
                singletonBeanMap.put(beanName,bean);
            }

            return bean;

        }
        return null;
    }
}
