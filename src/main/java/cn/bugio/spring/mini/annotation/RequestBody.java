package cn.bugio.spring.mini.annotation;

import java.lang.annotation.*;

/**
 * @author Vincent Vic
 * @version 1.0
 * @Description 返回序列化
 * @since 2021/1/20 19:25
 */

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestBody {

    /**
     * 是否需要正文内容。
     * <p>默认值为{@code true}，导致在case中引发异常
     * 没有正文内容。如果您愿意，请将此切换到{@code false}
     * 当正文内容为{@code null}时要传递的{@code null}。
     */
    boolean required() default true;

    /**
     * 参数名称，默认定义名称
     */
    String value() default "";
}
