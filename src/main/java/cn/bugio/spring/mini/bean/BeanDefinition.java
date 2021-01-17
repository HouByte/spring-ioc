package cn.bugio.spring.mini.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Vincent Vic
 * @version 1.0
 * @Description Bean 定义
 * @since 2021/1/17 22:24
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BeanDefinition {

    //Bean 类型
    private Class beanClass;
    //创建类型
    private String scope;
    //是否懒加载
    private Boolean isLazy;

}
