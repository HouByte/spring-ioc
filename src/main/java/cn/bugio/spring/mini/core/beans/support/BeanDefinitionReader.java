package cn.bugio.spring.mini.core.beans.support;

import cn.bugio.spring.mini.annotations.*;

import cn.bugio.spring.mini.core.beans.config.BeanDefinition;
import cn.bugio.spring.mini.constant.ScopeEnum;
import cn.bugio.spring.mini.exception.BeanException;
import cn.bugio.spring.mini.exception.ComponentScanException;
import cn.bugio.spring.mini.exception.ReflexException;
import cn.bugio.spring.mini.util.BeanUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Vincent Vic
 * @version 1.0
 * @Description 读取配置信息的工具类
 * @since 2021/1/23
 */

@Data
@Slf4j
public class BeanDefinitionReader {

    //配置读取
    private Class<?> configClass = null;

    //处理器列表
    private List<BeanPostProcessor> beanPostProcessorList = new ArrayList<>();

    public BeanDefinitionReader(Class<?> configClass) {
        this.configClass = configClass;
    }

    /**
     *  Load BeanDefinition
     * @return Map
     */
    public Map<String, BeanDefinition> doLoadBeanDefinitionMap() {
        List<Class> classList = doScanner(configClass);
        return doRegistyBeanDefinition(classList);
    }

    /**
     * 扫描得到Class
     */
    private List<Class> doScanner(Class<?> configClass) {

        //校验是否查找注解
        if (!configClass.isAnnotationPresent(ComponentScan.class)) {
            throw new ComponentScanException();
        }
        //获取组件扫描注解
        ComponentScan componentScanAnnotation = configClass.getAnnotation(ComponentScan.class);

        List<Class> classList = new ArrayList<>();

        //得到路径  cn.bugio.spring.demo.service
        String[] path = componentScanAnnotation.value();
        for (String s : path) {
            doScan(classList, s);
        }

        return classList;
    }

    /**
     * 扫描具体实现
     * @param classList
     * @param path
     */
    private void doScan(List<Class> classList, String path) {
        path = path.replace(".","/");

        //得到类加载器
        ClassLoader classLoader = BeanFactory.class.getClassLoader();
        URL url = classLoader.getResource(path);
        File file = new File(url.getFile()); //cn/bugio/spring/demo/service 文件夹


        //判断是否是文件夹
        if (file.isDirectory()) {
            //存储class文件路径
            List<String> fileList = new ArrayList<>();
            //获取目录
            File[] files = file.listFiles();
            //扫描到所有类class文件
            scanClassFile(fileList, files);

            String temPath = path.replace("/","\\");
            //转换并加载class
            for (String s : fileList) {
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

    /**
     * 注册BeanDefinition IOC
     */
    private Map<String, BeanDefinition> doRegistyBeanDefinition(List<Class> classList) {

        //BeanDefinition Map
        Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap();

        //过滤， BeanDefinition
        for (Class aClass : classList) {

            //如果是接口排除
            if (aClass.isInterface()) {
                continue;
            }

            //过滤 如果存在组件注解加入Bean
            if (!(aClass.isAnnotationPresent(Component.class) || aClass.isAnnotationPresent(RestController.class) || aClass.isAnnotationPresent(Service.class))){
                continue;
            }

            //BeanPostProcessor
            doBeanPostProcessor(aClass);

            //得到BeanName
            String beanName = BeanUtil.getBeanName(aClass);

            /**
             * 创建Bean定义，一开始默认单例，非懒加载
             */
            BeanDefinition beanDefinition = new BeanDefinition(beanName,aClass, ScopeEnum.SINGLETON,false);
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

            if (beanDefinitionMap.containsKey(beanName)){
                throw new BeanException( "It already exists :" + beanName);
            }
            log.info("add ioc bean :" + beanName);
            beanDefinitionMap.put(beanName,beanDefinition);
        }

        return beanDefinitionMap;
    }

    private void doBeanPostProcessor(Class aClass) {
        if (BeanPostProcessor.class.isAssignableFrom(aClass)) {
            try {
                BeanPostProcessor instance = (BeanPostProcessor) aClass.getDeclaredConstructor().newInstance();
                beanPostProcessorList.add(instance);
                log.info("add bean post processor list :" + aClass.getName());
            } catch (Exception e) {
                throw new ReflexException(e.toString());
            }
        }
    }

    public List<BeanPostProcessor> doBeanPostProcessorList() {
        return getBeanPostProcessorList();
    }
}
