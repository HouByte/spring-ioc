package cn.bugio.spring.demo;

import cn.bugio.spring.demo.service.UserService;
import cn.bugio.spring.demo.service.impl.UserInfoService;
import cn.bugio.spring.mini.annotation.ComponentScan;
import cn.bugio.spring.mini.constant.DispatchRequest;
import cn.bugio.spring.mini.context.SpringMiniApplictionContext;

import java.util.HashMap;
import java.util.Map;

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
        UserInfoService userInfoService = (UserInfoService) applictionContext.getBean("userInfoService");
        System.out.println(userInfoService);


        Map<String,String[]> params = new HashMap<>();
        String[] name = new String[]{"asdsdf"};
        String[] id = new String[]{"123456"};
        String[] addr = new String[]{"zxcvv"};
        params.put("name",name);
        params.put("id",id);
        params.put("addr",addr);

        DispatchRequest dispatchRequest = new DispatchRequest();
        dispatchRequest.setContextPath("");
        dispatchRequest.setParams(params);
        dispatchRequest.setUri("/demo/test");

        try {
            applictionContext.doDispatch(dispatchRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
