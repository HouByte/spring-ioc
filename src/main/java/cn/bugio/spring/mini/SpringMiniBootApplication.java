package cn.bugio.spring.mini;

import cn.bugio.spring.mini.annotations.ComponentScan;
import cn.bugio.spring.mini.core.beans.support.BeanConfig;
import cn.bugio.spring.mini.core.web.HttpServer;
import cn.bugio.spring.mini.core.web.HttpServerConfig;
import cn.bugio.spring.mini.exception.ComponentScanException;
import cn.bugio.spring.mini.util.YamlUtil;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Map;

/**
 * @author Vincent Vic
 * @version 1.0
 * @Description
 * @since 2021/1/23
 */
public class SpringMiniBootApplication {

    public static void run(Class clazz,String[] args){

        if (clazz.isAnnotationPresent(ComponentScan.class)) {
            ComponentScan annotation = (ComponentScan) clazz.getAnnotation(ComponentScan.class);
            if (annotation.value().length == 0){
                throw new ComponentScanException("No configuration Exception: ComponentScan length is 0");
            }
        } else {
            throw new ComponentScanException("No configuration : ComponentScan");
        }

        //配置初始化Bean
        BeanConfig beanConfig = new BeanConfig(clazz);

        //配置http web服务器
        boolean flag = true;
        HttpServerConfig config = null;

        String temPort = getArgsValue(args, "port");
        Integer port = 8080;
        if (temPort != null){
            //获取args参数
            port = Integer.valueOf(temPort);
        }


        //如果配置文件存在
        if (YamlUtil.isEnable()) {
            config = new HttpServerConfig();
            flag = lodingConfigFile(clazz,flag, config, port);
        }

        HttpServer httpServer = null;
        if (!flag){
            ComponentScan annotation =(ComponentScan) clazz.getAnnotation(ComponentScan.class);
            String[] packName = annotation.value();
            httpServer = new HttpServer(packName, port);
        }else {

            httpServer = new HttpServer(config);
        }

        httpServer.start();

    }

    /**
     * 加载配置文件
     * @param flag
     * @param config
     * @param port
     * @return
     */
    private static boolean lodingConfigFile(Class clazz,boolean flag, HttpServerConfig config, Integer port) {

        try {
            Map<String, Object> server = YamlUtil.getTypePropertieMap("server");
            if (server == null){
                flag = false;
            }
            if (port == null) {
                Object yamlPort = server.get("port");
                if (port != null) {
                    config.setPort((int) yamlPort);
                }
            }
            Object maxContentLength = server.get("maxContentLength");
            if (maxContentLength != null) {
                config.setMaxContentLength((int)maxContentLength);
            }



            String[] controllerBasePackage ;
            //如果注解存在值采用注解的
            if (clazz.isAnnotationPresent(ComponentScan.class)){
                ComponentScan componentScan =(ComponentScan) clazz.getAnnotation(ComponentScan.class);
                controllerBasePackage = componentScan.value();

                if (controllerBasePackage != null) {
                    config.setControllerBasePackage((String[]) controllerBasePackage);
                } else {
                    controllerBasePackage = (String[]) server.get("controllerBasePackage");
                }

            } else {
                controllerBasePackage = (String[]) server.get("controllerBasePackage");

            }

            if (controllerBasePackage != null) {
                config.setControllerBasePackage((String[]) controllerBasePackage);
            } else {
                //默认规则
                config.setControllerBasePackage(new String[]{clazz.getPackage().getName()});
            }


            List ignoreUrls =(List) server.get("ignoreUrls");
            if (ignoreUrls != null) {
                config.setIgnoreUrls(ignoreUrls);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 得到args参数
     * @param args
     * @param key
     * @return
     */
    private static String getArgsValue(String[] args,String key){
        for (String arg : args) {
            String[] split = arg.split("=");
            if (split.length == 2) {
                if (split[0].equals(key)) {
                    return split[1];
                }
            }
        }
        return null;
    }
}
