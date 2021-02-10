package cn.bugio.spring.mini.exception;


/**
 * @author Vincent Vic
 * @version 1.0
 * @Description 资源未发现异常类
 * @since 2021/1/18
 */
public class ResourceNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 5563265404641097547L;

    public ResourceNotFoundException() {
    }

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ResourceNotFoundException(Throwable cause) {
        super(cause);
    }

}
