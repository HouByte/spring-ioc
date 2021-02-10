package cn.bugio.spring.mini.annotations;

import java.lang.annotation.*;

/**
 * @author Vincent Vic
 * @version 1.0
 * @Description 不用拦截的url
 * @since 2021/1/26
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface IgnoreMapping {

    //不用拦截的url
    String[] excludeMapping() default {"/不用拦截的url"};
}
