package cn.bugio.spring.mini.annotations;

import java.lang.annotation.*;

/**
 * @author Vincent Vic
 * @version 1.0
 * @Description 自动装配
 * @since 2021/1/17
 */

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD,ElementType.METHOD,ElementType.CONSTRUCTOR })
@Documented
public @interface Autowired {

    /**
     * BeanName
     * @default 字段名
     */
    String value() default "";

}
