package cn.bugio.spring.mini.core.beans.support;

import cn.bugio.spring.mini.annotations.*;
import cn.bugio.spring.mini.constant.ScopeEnum;

import cn.bugio.spring.mini.core.beans.config.BeanDefinition;
import cn.bugio.spring.mini.core.beans.support.*;
import cn.bugio.spring.mini.exception.*;
import lombok.Data;

import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Vincent Vic
 * @version 1.0
 * @Description Spring Mini Appliction Context
 * @since 2021/1/17
 */

@Data
public class BeanFactory {


    /**
     * 得到bean
     * @param beanName
     * @return
     */
    public static Object getBean(String beanName){

        BeanDefinition beanDefinition = BeanRegistry.getBeanDefinition(beanName);
        FactoryBean factoryBean = new FactoryBean();
        if (beanDefinition == null){
            return null;
        }else if (ScopeEnum.PROTOTYPE.equals(beanDefinition.getScope())){
            //创建Bean
            return factoryBean.doCreateBean(beanName, beanDefinition);
        } else if (ScopeEnum.SINGLETON.equals(beanDefinition.getScope())){
            //从单例池中拿到Bean
            Object bean = BeanRegistry.getSingletonBean(beanName);
            if (null == bean){
                bean = factoryBean.doCreateBean(beanName,beanDefinition);
                if (null == bean){
                    throw new BeanException("BeanException bean is Null pointer");
                }
                BeanRegistry.registrySingletonBean(beanName,bean);
            }

            return bean;

        }
        return null;
    }

    /**
     * 重写getBean 使用class获取bean
     * @param clazz
     * @return
     */
    public static Object getBean(Class clazz){
        //得到BeanName
        String beanName = null ;//BeanUtil.getBeanName(clazz);
        return getBean(beanName);
    }

}
