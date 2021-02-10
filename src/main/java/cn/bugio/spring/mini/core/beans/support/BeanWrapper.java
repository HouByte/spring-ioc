package cn.bugio.spring.mini.core.beans.support;

import lombok.Data;

/**
 * @author Vincent Vic
 * @version 1.0
 * @Description Bean 代理类
 * @since 2021/1/23
 */

@Data
public class BeanWrapper {
    private Object wrapperInstance;
    private Class<?> wrapperClass;
    public BeanWrapper(Object instance) {
        this.wrapperInstance = instance;
        this.wrapperClass = wrapperInstance.getClass();
    }

    public Object getWrapperInstance() {
        return wrapperInstance;
    }
}
