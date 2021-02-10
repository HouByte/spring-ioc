package cn.bugio.spring.demo.service;

import cn.bugio.spring.mini.annotations.Component;
import cn.bugio.spring.mini.core.beans.support.BeanPostProcessor;
import cn.bugio.spring.mini.exception.BeanException;

/**
 * @author Vincent Vic
 * @version 1.0
 * @Description
 * @since 2021/1/19 21:05
 */

@Component
public class DemoBeanPostProcessor implements BeanPostProcessor {
    /**
     * 初始化前
     *
     * @param bean
     * @param beanName
     */
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeanException {
        System.out.println("初始化之前");
        return bean;
    }

    /**
     * 初始化后
     *
     * @param bean
     * @param beanName
     */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeanException {
        System.out.println("初始化之后");
        return bean;
    }
}
