package cn.bugio.spring.demo.service.impl;

import cn.bugio.spring.demo.service.User;
import cn.bugio.spring.mini.annotations.Autowired;

import cn.bugio.spring.mini.annotations.RequestMapping;
import cn.bugio.spring.mini.annotations.RestController;
import cn.bugio.spring.mini.core.beans.support.BeanNameAware;
import cn.bugio.spring.mini.core.beans.support.InitializingBean;
import cn.bugio.spring.mini.constant.RequestMethod;
import lombok.Data;

/**
 * @author Vincent Vic
 * @version 1.0
 * @Description
 * @since 2021/1/17 20:20
 */
@RestController("userInfoService")
@RequestMapping("/apps/")
@Data
public class UserInfoService implements BeanNameAware, InitializingBean {

    @Autowired
    private User user;

    private String beanName;

    private String userName;


    @Override
    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    @Override
    public void afterPropertiesSet() {
        userName = user.getName();
    }
}
