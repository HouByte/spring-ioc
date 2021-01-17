package cn.bugio.spring.mini.exception;

/**
 * @author Vincent Vic
 * @version 1.0
 * @Description
 * @since 2021/1/17 21:31
 */
public class ComponentScanException extends RuntimeException{

    private String msg;

    public ComponentScanException() {
    }

    public ComponentScanException(String msg) {
        this.msg = msg;
    }
}
