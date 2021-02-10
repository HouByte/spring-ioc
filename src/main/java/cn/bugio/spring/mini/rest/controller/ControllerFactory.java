package cn.bugio.spring.mini.rest.controller;

import cn.bugio.spring.mini.annotations.*;
import cn.bugio.spring.mini.util.ClassUtil;
import cn.bugio.spring.mini.rest.mapping.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.Set;

/**
 * @author Vincent Vic
 * @version 1.0
 * @Description 请求映射工厂类
 * @since 2021/1/21
 */

public final class ControllerFactory {

    private final static Logger logger = LoggerFactory.getLogger(ControllerFactory.class);

    /**
     * 注册Mpping
     * 
     * @param basePackages
     */
    public void registerController(String[] basePackages) {
        for (String basePackage : basePackages) {
            Set<Class<?>> controllerClasses = ClassUtil.findClassesByPackage(basePackage);

            for (Class<?> controllerClass : controllerClasses) {
                if (controllerClass.isAnnotationPresent(RestController.class)) {
                    registerClass(controllerClass);
                }
            }
        }

    }



    /**
     * 注册类
     * 
     * @param clazz
     */
    private void registerClass(Class<?> clazz) {
        String className = clazz.getName();
        logger.info("Registered REST Controller: {}", className);
//        ControllerBean bean = new ControllerBean(clazz, clazz.getAnnotation(RestController.class).singleton());
//        ControllerMappingRegistry.registerBean(className, bean);

        String url = null;
        RequestMapping requestMapping = clazz.getAnnotation(RequestMapping.class);
        if (requestMapping != null) {
            url = requestMapping.value();
        }
        Method[] methods = clazz.getMethods();
        for (Method method : methods) {
            RequestMappingRegisterStrategy strategy = null;
            // 遍历所有method，生成ControllerMapping并注册。
            if(method.getAnnotation(GetMapping.class) != null) {
                strategy = new GetMappingRegisterStrategy();
            } else if(method.getAnnotation(PostMapping.class) != null) {
                strategy = new PostMappingRegisterStrategy();
            } else if(method.getAnnotation(PutMapping.class) != null) {
                strategy = new PutMappingRegisterStrategy();
            } else if(method.getAnnotation(DeleteMapping.class) != null) {
                strategy = new DeleteMappingRegisterStrategy();
            } else if(method.getAnnotation(PatchMapping.class) != null) {
                strategy = new PatchMappingRegisterStrategy();
            }
            
            if(strategy != null) {
                RequestMappingRegisterContext mappingRegCtx = new RequestMappingRegisterContext(strategy);
                mappingRegCtx.registerMapping(clazz, url, method);
            }
        }
    }

}
