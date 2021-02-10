package cn.bugio.spring.demo.service.impl;

import cn.bugio.spring.mini.annotations.RestController;
import cn.bugio.spring.mini.annotations.RequestMapping;
import cn.bugio.spring.mini.annotations.RequestParam;



/**
 * @author Vincent Vic
 * @version 1.0
 * @Description
 * @since 2021/1/22 19:19
 */
@RestController
@RequestMapping("/demo/")
public class DemoTest {


    public void test(@RequestParam("id") String id, @RequestParam("name") String name, @RequestParam("addr") String addr){
        System.out.println(id+","+name+","+addr+",");
    }
}
