package cn.bugio.spring.mini.exception;

/**
 * @author Vincent Vic
 * @version 1.0
 * @Description
 * @since 2021/1/17 21:31
 */
public class BeanException extends RuntimeException{

    private String msg;

    public BeanException() {
    }

    public BeanException(String msg) {
        this.msg = msg;
    }
}
