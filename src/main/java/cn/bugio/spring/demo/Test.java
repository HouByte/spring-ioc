package cn.bugio.spring.demo;

import cn.bugio.spring.mini.SpringMiniBootApplication;
import cn.bugio.spring.mini.annotations.ComponentScan;
import org.apache.commons.lang3.ClassUtils;

/**
 * @author Vincent Vic
 * @version 1.0
 * @Description 测试
 * @since 2021/1/17 21:03
 */

@ComponentScan("cn.bugio.spring.demo.service")
public class Test {

    public static void main(String[] args) {

        SpringMiniBootApplication.run(Test.class,args);
    }
}
