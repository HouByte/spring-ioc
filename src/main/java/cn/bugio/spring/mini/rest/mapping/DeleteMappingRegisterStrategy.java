package cn.bugio.spring.mini.rest.mapping;



import cn.bugio.spring.mini.annotations.DeleteMapping;

import java.lang.reflect.Method;

/**
 * @author Vincent Vic
 * @version 1.0
 * @Description DELETE 请求映射注册策略类
 * @since 2021/1/23
 */

public final class DeleteMappingRegisterStrategy extends AbstractRequestMappingRegisterStrategy implements RequestMappingRegisterStrategy {
    
    /**
     * 得到控制器方法的Url
     * @param method
     * @return
     */
    @Override
    public String getMethodUrl(Method method) {
        if(method.getAnnotation(DeleteMapping.class) != null) {
            return method.getAnnotation(DeleteMapping.class).value();
        }
        return "";
    }

    /**
     * 得到Http请求的方法类型
     * @return
     */
    @Override
    public String getHttpMethod() {
        return "DELETE";
    }
    
    /**
     * 注册Mapping
     * @param url
     * @param mapping
     */
    @Override
    public void registerMapping(String url, ControllerMapping mapping) {
        ControllerMappingRegistry.getDeleteMappings().put(url, mapping);
    }

}
