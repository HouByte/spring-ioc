package cn.bugio.spring.mini.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Vincent Vic
 * @version 1.0
 * @Description 绑定异常
 * @since 2021/1/21
 */

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BindException extends RuntimeException{

    private String msg;
}
