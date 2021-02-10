package cn.bugio.spring.mini.annotations;

import cn.bugio.spring.mini.constant.RequestMethod;

import java.lang.annotation.*;


/**
 * @author Vincent Vic
 * @version 1.0
 * @Description Http 请求映射类
 * @since 2021/1/17
 */


@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface RequestMapping {

    String value() default "";

    RequestMethod method() default RequestMethod.GET;
}
