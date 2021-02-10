package cn.bugio.spring.mini.core.beans.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Vincent Vic
 * @version 1.0
 * @Description Bean 配置信息定义
 * @since 2021/1/17
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BeanDefinition {

    //工厂对象名称
    private String factoryBeanName;

    //Bean 类型
    private Class beanClass;
    //创建类型
    private String scope;
    //是否懒加载
    private Boolean isLazy;

}
