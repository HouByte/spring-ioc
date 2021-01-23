package cn.bugio.spring.demo.service.impl;

import cn.bugio.spring.mini.annotation.Controller;
import cn.bugio.spring.mini.annotation.Mapping;
import cn.bugio.spring.mini.annotation.RequestMapping;
import cn.bugio.spring.mini.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Vincent Vic
 * @version 1.0
 * @Description
 * @since 2021/1/22 19:19
 */
@Controller
@RequestMapping("/demo/")
public class DemoTest {

    @RequestMapping("test")
    public void test(@RequestParam("id") String id, @RequestParam("name") String name, @RequestParam("addr") String addr){
        System.out.println(id+","+name+","+addr+",");
    }
}
