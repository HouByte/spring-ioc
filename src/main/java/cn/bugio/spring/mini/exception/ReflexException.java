package cn.bugio.spring.mini.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Vincent Vic
 * @version 1.0
 * @Description 反射异常
 * @since 2021/1/17
 */

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ReflexException extends RuntimeException{

    private String msg;
}
