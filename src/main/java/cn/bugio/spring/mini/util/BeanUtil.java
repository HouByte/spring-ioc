package cn.bugio.spring.mini.util;

import cn.bugio.spring.mini.annotations.Component;
import cn.bugio.spring.mini.annotations.RestController;
import cn.bugio.spring.mini.annotations.Service;

/**
 * @author Vincent Vic
 * @version 1.0
 * @Description Bean 工具类
 * @since 2021/1/17 22:46
 */
public class BeanUtil {

    public static String getDefaultBeanName(Class clazz){
        String beanName = clazz.getSimpleName();
        char[] chars = beanName.substring(beanName.lastIndexOf(".")+1).toCharArray();
        if (chars[0] <= 90 && chars[0] >= 65) {
            chars[0] += 32;
        }
        return new String(chars);
    }

    public static String getBeanName(Class aClass) {
        String beanName = null;
        if (aClass.isAnnotationPresent(Component.class) ){
            Component componentAnnotation = (Component) aClass.getAnnotation(Component.class);
            beanName = componentAnnotation.value();
        } else if (aClass.isAnnotationPresent(RestController.class)){
            RestController controllerAnnotation = (RestController) aClass.getAnnotation(RestController.class);
            beanName = controllerAnnotation.value();
        } else if (aClass.isAnnotationPresent(Service.class)){
            Service serviceAnnotation = (Service) aClass.getAnnotation(Service.class);
            beanName = serviceAnnotation.value();
        }

        //如果BeanName不存在，使用默认规则
        if (beanName == null || "".equals(beanName)){
            beanName = BeanUtil.getDefaultBeanName(aClass);
        }
        return beanName;
    }
}
