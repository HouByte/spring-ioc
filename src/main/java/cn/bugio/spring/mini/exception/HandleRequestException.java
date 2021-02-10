package cn.bugio.spring.mini.exception;

/**
 * @author Vincent Vic
 * @version 1.0
 * @Description 处理请求异常类
 * @since 2021/1/18
 */

public final class HandleRequestException extends RuntimeException {

    private static final long serialVersionUID = -630225144002649999L;

    public HandleRequestException() {
    }

    public HandleRequestException(String message) {
        super(message);
    }

    public HandleRequestException(String message, Throwable cause) {
        super(message, cause);
    }

    public HandleRequestException(Throwable cause) {
        super(cause);
    }

}
