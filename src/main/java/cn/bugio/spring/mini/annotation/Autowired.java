package cn.bugio.spring.mini.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Vincent Vic
 * @version 1.0
 * @Description 自动装配
 * @since 2021/1/17 20:17
 */

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD,ElementType.METHOD,ElementType.CONSTRUCTOR })
public @interface Autowired {

}
