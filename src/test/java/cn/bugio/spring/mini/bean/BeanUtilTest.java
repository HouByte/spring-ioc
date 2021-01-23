package cn.bugio.spring.mini.bean;


import org.junit.jupiter.api.Test;

/**
 * @author Vincent Vic
 * @version 1.0
 * @Description
 * @since 2021/1/21 21:00
 */
class BeanUtilTest {


    @Test
    public void getDefaultBeanName() {
        String defaultBeanName = BeanUtil.getDefaultBeanName(BeanUtil.class);
        System.out.println(defaultBeanName);

    }
}