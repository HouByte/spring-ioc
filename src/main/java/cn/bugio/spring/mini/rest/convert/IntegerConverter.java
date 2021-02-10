package cn.bugio.spring.mini.rest.convert;

/**
 * @author Vincent Vic
 * @version 1.0
 * @Description 整数转换器
 * @since 2021/1/23
 */
final class IntegerConverter implements Converter<Integer> {

    /**
     * 类型转换
     * 
     * @param source
     * @return
     */
    @Override
    public Integer convert(Object source) {
        return Integer.parseInt(source.toString());
    }

}
