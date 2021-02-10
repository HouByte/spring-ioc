package cn.bugio.spring.mini.rest.controller;

import lombok.Data;

/**
 * @author Vincent Vic
 * @version 1.0
 * @Description 注册
 * @since 2021/2/10
 */
@Data
public class ExceptionHandlerRegistry {

    /**
     * 异常处理器
     *
     */
    private static ExceptionHandler exceptionHandler;

    public static ExceptionHandler getExceptionHandler() {
        return exceptionHandler;
    }

    public static void setExceptionHandler(ExceptionHandler exceptionHandler) {
        ExceptionHandlerRegistry.exceptionHandler = exceptionHandler;
    }
}
