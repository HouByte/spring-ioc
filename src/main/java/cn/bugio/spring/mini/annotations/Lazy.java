package cn.bugio.spring.mini.annotations;

import java.lang.annotation.*;

/**
 * @author Vincent Vic
 * @version 1.0
 * @Description 懒加载
 * @since 2021/1/17
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface Lazy {
}
