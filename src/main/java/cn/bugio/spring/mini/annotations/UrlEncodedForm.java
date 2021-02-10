package cn.bugio.spring.mini.annotations;

import java.lang.annotation.*;


/**
 * @author Vincent Vic
 * @version 1.0
 * @Description UrlEncodedForm 注解
 * @since 2021/1/17
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface UrlEncodedForm {

}
