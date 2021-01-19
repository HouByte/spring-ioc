package cn.bugio.spring.demo.service;

import cn.bugio.spring.mini.annotation.Autowired;
import cn.bugio.spring.mini.annotation.Component;
import cn.bugio.spring.mini.annotation.ComponentScan;
import cn.bugio.spring.mini.annotation.Scope;
import cn.bugio.spring.mini.bean.BeanNameAware;
import cn.bugio.spring.mini.bean.InitializingBean;
import lombok.Data;

/**
 * @author Vincent Vic
 * @version 1.0
 * @Description
 * @since 2021/1/17 20:20
 */
@Component("userService")
@Data
public class UserService implements BeanNameAware, InitializingBean {

    @Autowired
    private User user;

    private String beanName;

    private String userName;

    public void test(){
        System.out.println(user);
    }

    @Override
    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    @Override
    public void afterPropertiesSet() {
        userName = user.getName();
    }
}
