package cn.bugio.spring.mini.annotations;

import java.lang.annotation.*;


/**
 * @author Vincent Vic
 * @version 1.0
 * @Description REST 控制器注解
 * @since 2021/1/17
 */

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RestController {

    /**
     * BeanName
     * @default 字段名
     */
    String value() default "";
    
    boolean singleton() default true;
    
}
