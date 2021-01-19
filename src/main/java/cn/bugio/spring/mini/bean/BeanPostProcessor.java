package cn.bugio.spring.mini.bean;

import cn.bugio.spring.mini.exception.BeanException;

/**
 * @author Vincent Vic
 * @version 1.0
 * @Description Bean Post Processor
 * @since 2021/1/19 20:57
 */
public interface BeanPostProcessor {

    /**
     * 初始化前
     */
    default Object postProcessBeforeInitialization(Object bean, String beanName) throws BeanException {
        return bean;
    };

    /**
     * 初始化后
     */
    default Object postProcessAfterInitialization(Object bean, String beanName) throws BeanException {
        return bean;
    };
}
