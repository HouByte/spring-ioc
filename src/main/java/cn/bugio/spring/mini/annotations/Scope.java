package cn.bugio.spring.mini.annotations;

import java.lang.annotation.*;

/**
 * @author Vincent Vic
 * @version 1.0
 * @Description 原型模式
 * @since 2021/1/17
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface Scope {

    /**
     * singleton or prototype
     * @default "singleton"
     * @return
     */
    String value() default "singleton";
}
