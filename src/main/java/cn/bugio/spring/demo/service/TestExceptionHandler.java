package cn.bugio.spring.demo.service;

import cn.bugio.spring.mini.annotations.Component;
import cn.bugio.spring.mini.rest.controller.ExceptionHandler;

/**
 * @author Vincent Vic
 * @version 1.0
 * @Description
 * @since 2021/2/10
 */
@Component("TestExceptionHandler")
public class TestExceptionHandler implements ExceptionHandler {
    /**
     * 处理异常
     *
     * @param e
     */
    @Override
    public void doHandle(Exception e) {
        System.out.println("121111");
    }
}
