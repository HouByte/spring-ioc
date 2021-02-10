package cn.bugio.spring.demo.service;

import cn.bugio.spring.mini.annotations.*;
import cn.bugio.spring.mini.annotations.Autowired;
import cn.bugio.spring.mini.annotations.RestController;
import cn.bugio.spring.mini.core.beans.support.BeanNameAware;
import cn.bugio.spring.mini.core.beans.support.InitializingBean;
import cn.bugio.spring.mini.rest.constant.ResponseEntity;
import lombok.Data;

/**
 * @author Vincent Vic
 * @version 1.0
 * @Description
 * @since 2021/1/17 20:20
 */
@RestController("userService")
@RequestMapping("/user/")
@Data
public class UserService implements BeanNameAware, InitializingBean {

    @Autowired
    private User user;

    private String beanName;

    private String userName;


    @GetMapping("XXX")
    public ResponseEntity<?> test(){
        System.out.println(user);
        return ResponseEntity.ok(user);
    }


    public void test2(String name){
        System.out.println(user);
    }


    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }


    public void afterPropertiesSet() {
        userName = user.getName();
    }
}
