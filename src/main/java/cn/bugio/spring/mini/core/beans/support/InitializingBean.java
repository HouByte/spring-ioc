package cn.bugio.spring.mini.core.beans.support;

/**
 * @author Vincent Vic
 * @version 1.0
 * @Description 初始化Bean
 * @since 2021/1/19
 */
public interface InitializingBean {
    void afterPropertiesSet();
}
