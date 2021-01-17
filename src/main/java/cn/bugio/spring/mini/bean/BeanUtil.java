package cn.bugio.spring.mini.bean;

/**
 * @author Vincent Vic
 * @version 1.0
 * @Description Bean 工具类
 * @since 2021/1/17 22:46
 */
public class BeanUtil {

    public static String getDefaultBeanName(Class clazz){
        String beanName = clazz.getName();
        beanName = beanName.substring(0,1).toLowerCase() + beanName.substring(1);
        return beanName;
    }
}
