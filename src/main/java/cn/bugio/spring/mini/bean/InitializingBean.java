package cn.bugio.spring.mini.bean;

/**
 * @author Vincent Vic
 * @version 1.0
 * @Description 初始化Bean
 * @since 2021/1/19 20:35
 */
public interface InitializingBean {
    void afterPropertiesSet();
}
