package cn.bugio.spring.mini.core.beans.support;

import cn.bugio.spring.mini.annotations.IgnoreMapping;
import cn.bugio.spring.mini.constant.ScopeEnum;
import cn.bugio.spring.mini.core.beans.config.BeanDefinition;
import cn.bugio.spring.mini.rest.controller.ExceptionHandler;
import cn.bugio.spring.mini.rest.controller.ExceptionHandlerRegistry;
import cn.bugio.spring.mini.rest.interceptor.Interceptor;
import cn.bugio.spring.mini.rest.interceptor.InterceptorRegistry;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * @author Vincent Vic
 * @version 1.0
 * @Description
 * @since 2021/1/25
 */
@Slf4j
public class BeanConfig {

    //获取配置类class
    private Class configClass;

    private BeanDefinitionReader reader ;


    public BeanConfig(Class configClass) {
        this.configClass = configClass;

        //1.读取配置文件
        reader = new BeanDefinitionReader(configClass);
        BeanRegistry.setBeanDefinitionMap(reader.doLoadBeanDefinitionMap());
        //读取
        BeanRegistry.setBeanPostProcessorList( reader.doBeanPostProcessorList());

        //2.扫描异常和拦截处理器
        doExceptionOrInterceptorHandler();


        //基于class创建单例Bean IOC
        doInstanceSingletonBean();
    }

    private void doExceptionOrInterceptorHandler() {
        //扫描ExceptionHandler or Interceptor
        for (Map.Entry<String, BeanDefinition> beanDefinitionEntry : BeanRegistry.getBeanDefinitionMap().entrySet()) {
            Class beanClass = beanDefinitionEntry.getValue().getBeanClass();
            if (ExceptionHandler.class.isAssignableFrom(beanClass)) {
                log.info("add ExceptionHandler : "+beanClass.getName());
                // 全局异常处理
                ExceptionHandlerRegistry.setExceptionHandler((ExceptionHandler) BeanFactory.getBean(beanClass));
            } else if (Interceptor.class.isAssignableFrom(beanClass) ) {
                log.info("add Interceptor : "+beanClass.getName());
                String[] ignoreMapping = new String[0];
                if (beanClass.isAnnotationPresent(IgnoreMapping.class)){
                    ignoreMapping = ((IgnoreMapping)beanClass.getAnnotation(IgnoreMapping.class)).excludeMapping();
                }
                InterceptorRegistry.addInterceptor((Interceptor) BeanFactory.getBean(beanClass), ignoreMapping);
            }

        }
    }

    private void doInstanceSingletonBean() {
        FactoryBean factoryBean = new FactoryBean();
        for (Map.Entry<String, BeanDefinition> beanDefinitionEntry : BeanRegistry.getBeanDefinitionMap().entrySet()) {
            String beanName = beanDefinitionEntry.getKey();
            BeanDefinition beanDefinition = beanDefinitionEntry.getValue();

            //不是懒加载并且是是单例的情况下
            if (!beanDefinition.getIsLazy() && ScopeEnum.SINGLETON.equals(beanDefinition.getScope())){
                //已经存在的情况无需重复
                if (!BeanRegistry.containsKey(beanName)) {
                    //创建Bean
                    Object bean = factoryBean.doCreateBean(beanName, beanDefinition);
                    //加入单例池
                    BeanRegistry.registrySingletonBean(beanName, bean);
                    log.info("registry Singleton Bean :" + beanName);
                }

            }
        }
    }
}
