package cn.bugio.spring.mini.core.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Vincent Vic
 * @version 1.0
 * @Description
 * @since 2021/1/26
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class HttpServerConfig {
    //排除url
    private List<String> ignoreUrls = new LinkedList<>();
    //端口
    private int port = 8080;
    //Http最大内容长度（默认 为10M）
    private int maxContentLength  = 1024 * 1024 * 50;
    //Controller所在包
    private String[] controllerBasePackage;


    public void copyConfig(HttpServerConfig config){
        if (config.getPort() != 0 && config.getPort() != this.getPort()){
            this.setPort(config.getPort());
        }

        if (config.getMaxContentLength() != 0 && config.getMaxContentLength() != this.getMaxContentLength()){
            this.setMaxContentLength(config.getMaxContentLength());
        }

        if (config.getControllerBasePackage() != null && !config.getControllerBasePackage().equals(this.controllerBasePackage)){
            this.setControllerBasePackage(config.getControllerBasePackage());
        }

        if (config.getIgnoreUrls() != null){
            for (String ignoreUrl : config.getIgnoreUrls()) {
                ignoreUrls.add(ignoreUrl);
            }
        }


    }


}
