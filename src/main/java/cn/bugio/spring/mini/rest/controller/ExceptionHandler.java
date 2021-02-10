package cn.bugio.spring.mini.rest.controller;


/**
 * @author Vincent Vic
 * @version 1.0
 * @Description 异常处理器
 * @since 2021/1/21
 */
public interface ExceptionHandler {

    /**
     * 处理异常
     * @param e
     */
    void doHandle(Exception e);
    
}
