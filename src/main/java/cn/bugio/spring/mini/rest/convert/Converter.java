package cn.bugio.spring.mini.rest.convert;

/**
 * @author Vincent Vic
 * @version 1.0
 * @Description 数据转换器接口
 * @since 2021/1/23
 */

public interface Converter<T> {

    /**
     * 类型转换
     * 
     * @param source
     * @return
     */
    T convert(Object source);

}
