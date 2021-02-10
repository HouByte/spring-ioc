package cn.bugio.spring.mini.rest.convert;

/**
 * @author Vincent Vic
 * @version 1.0
 * @Description 长整数转换器
 * @since 2021/1/23
 */

final class LongConverter implements Converter<Long> {

    /**
     * 类型转换
     * 
     * @param source
     * @return
     */
    @Override
    public Long convert(Object source) {
        return Long.parseLong(source.toString());
    }

}