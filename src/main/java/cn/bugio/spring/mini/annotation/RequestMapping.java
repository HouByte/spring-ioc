package cn.bugio.spring.mini.annotation;

import cn.bugio.spring.mini.constant.RequestMethod;

import java.lang.annotation.*;

/**
 * @author Vincent Vic
 * @version 1.0
 * @Description
 * @since 2021/1/20 19:38
 */

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Mapping
public @interface RequestMapping {

    /**
     * 为此映射指定一个名称。
     * 在类型级别和方法级别都支持！在两个级别上使用时，组合名称是通过以“#”作为分隔符串联而派生的。
     */
    String name() default "";

    /**
     *此批注表示的主映射。
     *<p>这是{@link#path}的别名。例如，
     *{@code@RequestMapping（“/foo”）}等价于
     *{@code@RequestMapping（path=“/foo”）}。
     *<p><b>类型级别和方法级别都支持！</b>
     *在类型级别使用时，所有方法级别的映射都继承
     *这是一个主映射，为特定的处理程序方法缩小了它的范围。
     *<p><strong>注</strong>：未映射到任何路径的处理程序方法
     *显式映射到空路径。
     */
    String[] value() default {};

    /**
     *此批注表示的主映射。
     *{@code@RequestMapping（“/foo”）}等价于
     *{@code@RequestMapping（path=“/foo”）}。
     *<p><b>类型级别和方法级别都支持！</b>
     *在类型级别使用时，所有方法级别的映射都继承
     *这是一个主映射，为特定的处理程序方法缩小了它的范围。
     *<p><strong>注</strong>：未映射到任何路径的处理程序方法
     *显式映射到空路径。
     */
    String[] path() default {};


    /**
     *要映射到的HTTP请求方法，缩小了主映射的范围：
     *获取、发布、头部、选项、放置、修补、删除、跟踪。
     *<p><b>类型级别和方法级别都支持！你知道吗</b>
     *在类型级使用时，所有方法级映射都继承此
     *HTTP方法限制。
     */
    RequestMethod[] method() default {};



    /**
     * 待实现
     *映射请求的参数，缩小了主映射的范围。
     *<p>任何环境的格式都相同：一系列“myParam=myValue”样式
     *表达式，仅当找到每个此类参数时才映射请求
     *得到给定的值。表达式可以用“！=“操作员，
     *就像“myParam！=myValue“.”还支持“myParam”样式表达式，
     *这些参数必须存在于请求中（允许
     *任何值）。最后，“！myParam”样式表达式表示
     *指定的参数不应该出现在请求中。
     *<p><b>类型级别和方法级别都支持！你知道吗</b>
     *在类型级使用时，所有方法级映射都继承此
     *参数限制。
     */
    //String[] params() default {};

    /**
     *映射请求的头，缩小了主映射的范围。
     *<p>任何环境的格式都相同：一系列“My Header=myValue”样式
     *表达式，仅当找到每个这样的头时才映射请求
     *得到给定的值。表达式可以用“！=“操作员，
     *就像“我的头！=myValue“.”还支持“我的标题”样式表达式，
     *请求中必须存在这样的头（允许
     *任何值）。最后，“！“我的标题”样式表达式表示
     *指定的头不应该出现在请求中。
     *<p>还支持媒体类型通配符（*），例如Accept
     *和内容类型。例如，
     *<pre class=“code”>
     *&amp;#064；请求映射（value=“/something”，headers=“content type=text/*”）
     *</pre>
     *将匹配内容类型为“text/html”、“text/plain”等的请求。
     *<p><b>类型级别和方法级别都支持！你知道吗</b>
     *在类型级使用时，所有方法级映射都继承此
     *收割台限制。
     */
    String[] headers() default {};
}
