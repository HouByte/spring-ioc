package cn.bugio.spring.mini.annotations;

import java.lang.annotation.*;


/**
 * @author Vincent Vic
 * @version 1.0
 * @Description Http GET 方法注解
 * @since 2021/1/17
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface GetMapping {

    String value() default "";

}
