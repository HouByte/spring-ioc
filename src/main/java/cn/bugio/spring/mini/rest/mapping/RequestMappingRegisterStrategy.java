package cn.bugio.spring.mini.rest.mapping;

import java.lang.reflect.Method;


/**
 * @author Vincent Vic
 * @version 1.0
 * @Description 请求映射注册策略接口
 * @since 2021/1/23
 */
public interface RequestMappingRegisterStrategy {
    
    /**
     * 注册请求映射
     * @param clazz
     * @param baseUrl
     * @param method
     */
    void register(Class<?> clazz, String baseUrl, Method method);

}
