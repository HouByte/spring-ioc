package cn.bugio.spring.mini.rest.convert;

/**
 * @author Vincent Vic
 * @version 1.0
 * @Description 字符串转换器
 * @since 2021/1/23
 */

final class StringConverter implements Converter<String> {

    /**
     * 类型转换
     * 
     * @param source
     * @return
     */
    @Override
    public String convert(Object source) {
        return source.toString();
    }

}
