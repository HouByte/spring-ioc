package cn.bugio.spring.mini.core.web;

import cn.bugio.spring.mini.annotations.IgnoreMapping;
import cn.bugio.spring.mini.core.beans.config.BeanDefinition;
import cn.bugio.spring.mini.core.beans.support.BeanFactory;
import cn.bugio.spring.mini.core.beans.support.BeanRegistry;
import cn.bugio.spring.mini.rest.controller.ExceptionHandler;
import cn.bugio.spring.mini.rest.controller.ExceptionHandlerRegistry;
import cn.bugio.spring.mini.rest.interceptor.Interceptor;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * @author Vincent Vic
 * @version 1.0
 * @Description
 * @since 2021/1/26
 */

@Slf4j
public class HttpServer {

    public HttpServerConfig config;

    public HttpServer(String[] packageName) {
        defaultConfig();
        config.setControllerBasePackage(packageName);
    }

    public HttpServer(String[] packageName,Integer port) {
        defaultConfig();
        config.setControllerBasePackage(packageName);
        if (port != null){
            config.setPort(port);
        }
    }

    public HttpServer(HttpServerConfig config) {
        defaultConfig();
        if (config == null){
            return;
        }
        //复制有价值的值
        this.config.copyConfig(config);

    }

    /**
     * 默认配置
     */
    private void defaultConfig(){
        config = new HttpServerConfig();
        config.getIgnoreUrls().add("/favicon.ico");
        config.setPort(8080);
    }



    public void start(){
        //配置Http服务器
        WebServer server = getWebServer();

        try {
            log.info("Http server run [prot]: " + config.getPort());
            server.start();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    private WebServer getWebServer() {
        // 忽略指定url
        for (String ignoreUrl : config.getIgnoreUrls()) {
            WebServer.getIgnoreUrls().add(ignoreUrl);
        }

        // 设置监听端口号
        WebServer server = new WebServer(config.getPort());

        // 设置Http最大内容长度（默认 为10M）
        server.setMaxContentLength(config.getMaxContentLength());

        // 设置Controller所在包
        server.setControllerBasePackage(config.getControllerBasePackage());
        return server;
    }
}
