package cn.bugio.spring.demo;

import cn.bugio.spring.demo.service.UserService;
import cn.bugio.spring.mini.annotation.ComponentScan;
import cn.bugio.spring.mini.context.SpringMiniApplictionContext;
import cn.bugio.spring.demo.service.User;

/**
 * @author Vincent Vic
 * @version 1.0
 * @Description 测试
 * @since 2021/1/17 21:03
 */

@ComponentScan("cn.bugio.spring.demo.service")
public class Test {

    public static void main(String[] args) {

        // 启动Spring 扫描 ---> 创建非慢加载Bean
        SpringMiniApplictionContext applictionContext = new SpringMiniApplictionContext(Test.class);

        UserService userService = (UserService) applictionContext.getBean("userService");
        System.out.println(userService);
        userService.test();
        UserService userService2 = (UserService) applictionContext.getBean("userService");
        System.out.println(userService2);
    }
}
