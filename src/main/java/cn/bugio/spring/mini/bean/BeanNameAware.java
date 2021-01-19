package cn.bugio.spring.mini.bean;

/**
 * @author Vincent Vic
 * @version 1.0
 * @Description Bean name 回调
 * @since 2021/1/19 20:24
 */
public interface BeanNameAware {

    void setBeanName(String beanName);
}
