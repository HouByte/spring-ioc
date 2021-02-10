package cn.bugio.spring.demo.service;

import cn.bugio.spring.mini.annotations.Component;
import cn.bugio.spring.mini.rest.constant.HttpResponse;
import cn.bugio.spring.mini.rest.interceptor.Interceptor;
import io.netty.handler.codec.http.FullHttpRequest;

/**
 * @author Vincent Vic
 * @version 1.0
 * @Description
 * @since 2021/2/10
 */

@Component("testInterceptor")
public class TestInterceptor implements Interceptor {
    /**
     * 在业务处理器处理请求之前被调用
     * 如果返回false， 从当前的拦截器往回执行所有拦截器的afterCompletion()，再退出拦截器链。
     * 如果返回true，执行下一个拦截器，直到所有的拦截器都执行完毕，再执行被拦截的Controller。
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(FullHttpRequest request, HttpResponse response) throws Exception {
        System.out.println("1111");
        return false;
    }

    /**
     * 业务处理器执行完成之后执行
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @Override
    public void postHandle(FullHttpRequest request, HttpResponse response) throws Exception {
        System.out.println("1112");
    }

    /**
     * 完全处理完请求后被调用
     * 当有拦截器抛出异常时，会从当前拦截器往回执行所有的拦截器的afterCompletion()。
     *
     * @param request
     * @param response
     */
    @Override
    public void afterCompletion(FullHttpRequest request, HttpResponse response) {
        System.out.println("1113");
    }
}
