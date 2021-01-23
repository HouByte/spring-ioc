package cn.bugio.spring.mini.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Vincent Vic
 * @version 1.0
 * @Description 控制层定义
 * @since 2021/1/20 20:21
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class MappingDefinition {

    /**
     * Bean Name
     */
    String beanName;

    /**
     * 具体实现方法名
     */
    String methodName;

    /**
     * 请求参数设置
     */
    String[] params;

    /**
     * 请求头设置
     */
    String[] headers;
}
