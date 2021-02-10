package cn.bugio.spring.mini.annotations;

import java.lang.annotation.*;

/**
 * @author Vincent Vic
 * @version 1.0
 * @Description 组件扫描
 * @since 2021/1/17
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface ComponentScan {

    String[] value() default "";

}
