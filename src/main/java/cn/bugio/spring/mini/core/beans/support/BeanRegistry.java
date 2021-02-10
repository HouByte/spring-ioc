package cn.bugio.spring.mini.core.beans.support;

import cn.bugio.spring.mini.core.beans.config.BeanDefinition;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Vincent Vic
 * @version 1.0
 * @Description
 * @since 2021/1/25
 */
public class BeanRegistry {

    //BeanDefinition Map
    private static Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap();

    //处理器列表
    private static List<BeanPostProcessor> beanPostProcessorList = new ArrayList<>();



    //SingletonBean Map 单例池
    private static Map<String, Object> singletonBeanMap = new ConcurrentHashMap();

    public static BeanDefinition getBeanDefinition(String beanName) {
        return beanDefinitionMap.get(beanName);
    }

    public static void setBeanDefinitionMap(Map<String, BeanDefinition> beanDefinitionMap) {
        BeanRegistry.beanDefinitionMap = beanDefinitionMap;
    }

    public static Map<String, BeanDefinition> getBeanDefinitionMap() {
        return beanDefinitionMap;
    }

    public static void registryBeanDefinition(String beanName, BeanDefinition definition) {
        BeanRegistry.beanDefinitionMap.put(beanName,definition);
    }

    public static List<BeanPostProcessor> getBeanPostProcessorList() {
        return beanPostProcessorList;
    }

    public static void registryBeanPostProcessor(BeanPostProcessor beanPostProcessor) {
        BeanRegistry.beanPostProcessorList.add(beanPostProcessor);
    }

    public static void setBeanPostProcessorList(List<BeanPostProcessor> beanPostProcessorList) {
        BeanRegistry.beanPostProcessorList = beanPostProcessorList;
    }

    public static Object getSingletonBean(String beanName) {
        return singletonBeanMap.get(beanName);
    }

    public static void registrySingletonBean(String beanName, Object obj) {
        BeanRegistry.singletonBeanMap.put(beanName,obj);
    }

    public static Map<String, Object> getSingletonBeanMap() {
        return singletonBeanMap;
    }

    public static boolean containsKey(String baneName){
        return BeanRegistry.singletonBeanMap.containsKey(baneName);
    }
}
