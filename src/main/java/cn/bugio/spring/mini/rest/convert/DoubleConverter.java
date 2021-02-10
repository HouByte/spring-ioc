package cn.bugio.spring.mini.rest.convert;


/**
 * @author Vincent Vic
 * @version 1.0
 * @Description 双精度转换器
 * @since 2021/1/23
 */
final class DoubleConverter implements Converter<Double> {

    /**
     * 类型转换
     * 
     * @param source
     * @return
     */
    @Override
    public Double convert(Object source) {
        return Double.parseDouble(source.toString());
    }

}
