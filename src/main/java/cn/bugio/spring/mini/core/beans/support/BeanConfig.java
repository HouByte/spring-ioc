package cn.bugio.spring.mini.core.beans.support;

import cn.bugio.spring.mini.constant.ScopeEnum;
import cn.bugio.spring.mini.core.beans.config.BeanDefinition;

import java.util.Map;

/**
 * @author Vincent Vic
 * @version 1.0
 * @Description
 * @since 2021/1/25
 */
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


        //基于class创建单例Bean IOC
        doInstanceSingletonBean();
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
                }

            }
        }
    }
}
