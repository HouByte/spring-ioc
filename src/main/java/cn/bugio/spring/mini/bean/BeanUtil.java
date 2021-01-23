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
        char[] chars = beanName.substring(beanName.lastIndexOf(".")+1).toCharArray();
        if (chars[0] <= 90 && chars[0] >= 65) {
            chars[0] += 32;
        }
        return new String(chars);
    }
}
