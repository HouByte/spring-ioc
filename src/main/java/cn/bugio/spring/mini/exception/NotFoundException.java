package cn.bugio.spring.mini.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Vincent Vic
 * @version 1.0
 * @Description 404 异常
 * @since 2021/1/17
 */


@AllArgsConstructor
@Data
public class NotFoundException extends RuntimeException{

    private int state;

    private String msg;

    /**
     * Constructs a new runtime exception with {@code null} as its
     * detail message.  The cause is not initialized, and may subsequently be
     * initialized by a call to {@link #initCause}.
     */
    public NotFoundException() {
        this.state = 404;
        this.msg = "404 Not Found!";
    }

    public NotFoundException(String msg) {
        this.state = 404;
        this.msg = msg;
    }
}
