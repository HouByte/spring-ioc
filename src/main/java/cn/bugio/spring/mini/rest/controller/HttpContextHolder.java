package cn.bugio.spring.mini.rest.controller;

import cn.bugio.spring.mini.rest.constant.HttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;

/**
 * @author Vincent Vic
 * @version 1.0
 * @Description Http 上下文持有者
 * @since 2021/1/21
 */

public final class HttpContextHolder {

    private static final ThreadLocal<FullHttpRequest> LOCAL_REQUEST = new ThreadLocal<FullHttpRequest>();
    
    private static final ThreadLocal<HttpResponse> LOCAL_RESPONSE = new ThreadLocal<HttpResponse>();

    /**
     * 设置Http Request
     * @param request
     */
    public static void setRequest(FullHttpRequest request) {
        LOCAL_REQUEST.set(request);
    }
    
    /**
     * 得到Http Request
     * @return
     */
    public static FullHttpRequest getRequest() {
        return LOCAL_REQUEST.get();
    }
    
    /**
     * 删除Http Request
     */
    public static void removeRequest() {
        LOCAL_REQUEST.remove();
    }
    
    /**
     * 设置Http Response
     * @param response
     */
    public static void setResponse(HttpResponse response) {
        LOCAL_RESPONSE.set(response);
    }
    
    /**
     * 得到Http Response
     * @return
     */
    public static HttpResponse getResponse() {
        return LOCAL_RESPONSE.get();
    }
    
    /**
     * 删除Http Response
     */
    public static void removeResponse() {
        LOCAL_RESPONSE.remove();
    }

}
