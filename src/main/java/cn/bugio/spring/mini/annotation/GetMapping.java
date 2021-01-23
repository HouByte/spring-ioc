package cn.bugio.spring.mini.annotation;

import cn.bugio.spring.mini.constant.RequestMethod;

import java.lang.annotation.*;

/**
 * @author Vincent Vic
 * @version 1.0
 * @Description Get Mapping
 * @since 2021/1/20 19:49
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@RequestMapping(method = RequestMethod.GET)
public @interface GetMapping {

    /**
     * 为此映射指定一个名称。
     * 在类型级别和方法级别都支持！在两个级别上使用时，组合名称是通过以“#”作为分隔符串联而派生的。
     */
    String name() default "";

    /**
     * 默认路径别名
     */
    String[] value() default {};

    /**
     * 路径
     */
    String[] path() default {};

    /**
     * 请求参数设置
     */
    String[] params() default {};

    /**
     * 请求头设置
     */
    String[] headers() default {};
}
