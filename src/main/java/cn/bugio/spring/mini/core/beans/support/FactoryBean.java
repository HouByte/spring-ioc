package cn.bugio.spring.mini.core.beans.support;

import cn.bugio.spring.mini.annotations.Autowired;
import cn.bugio.spring.mini.core.beans.config.BeanDefinition;
import cn.bugio.spring.mini.exception.BeanException;

import java.lang.reflect.Field;

/**
 * @author Vincent Vic
 * @version 1.0
 * @Description
 * @since 2021/1/25
 */
public class FactoryBean {

    /**
     * 创建实例并且自动装配
     * @param beanName
     * @param beanDefinition
     * @return
     */
    public Object doCreateBean(String beanName, BeanDefinition beanDefinition) {

        //1.创建实例
        Object bean = newInstanceBean(beanDefinition.getBeanClass());

        //2.依赖填充 DI
        Class beanClass = beanDefinition.getBeanClass();
        populateBean(bean, beanClass);

        //3.Aware
        if (bean instanceof BeanNameAware){
            ((BeanNameAware)bean).setBeanName(beanName);
        }

        //初始化之前
        for (BeanPostProcessor beanPostProcessor : BeanRegistry.getBeanPostProcessorList()) {
            bean = beanPostProcessor.postProcessBeforeInitialization(bean,beanName);
        }

        //4.InitializingBean
        if (bean instanceof InitializingBean){
            ((InitializingBean)bean).afterPropertiesSet();
        }

        //初始化之后
        for (BeanPostProcessor beanPostProcessor : BeanRegistry.getBeanPostProcessorList()) {
            bean = beanPostProcessor.postProcessAfterInitialization(bean,beanName);
        }

//        // TODO 封装成BeanWrapper
//
//        BeanWrapper beanWrapper = new BeanWrapper(bean);

        return bean;
    }

    private Object newInstanceBean(Class beanClass){
        //1.实例化
        try {
            return beanClass.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            throw new BeanException("doCreateBean : " + e.toString());
        }


    }

    private void populateBean(Object bean, Class beanClass)  {
        //扫描字段
        for (Field declaredField : beanClass.getDeclaredFields()) {
            if (declaredField.isAnnotationPresent(Autowired.class)) {

                //得到Bean Name
                String fieldBeanName = declaredField.getName();
                Autowired autowired = (Autowired)declaredField.getAnnotation(Autowired.class);
                if (!"".equals(autowired.value().trim())){
                    fieldBeanName = autowired.value();
                }
                //得到值
                Object fieldBean = BeanFactory.getBean(fieldBeanName);
                if (null == fieldBean){
                    throw new BeanException("Null pointer exception from Autowired Bean: Auto assembly could not found bean");
                }
                declaredField.setAccessible(true);
                //赋值
                try {
                    declaredField.set(bean,fieldBean);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
