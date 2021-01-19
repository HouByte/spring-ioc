package cn.bugio.spring.demo.service;

import cn.bugio.spring.mini.annotation.Component;
import lombok.Data;

/**
 * @author Vincent Vic
 * @version 1.0
 * @Description 用户
 * @since 2021/1/17 20:22
 */
@Component("user")
@Data
public class User {
    private String name = "123";
}
