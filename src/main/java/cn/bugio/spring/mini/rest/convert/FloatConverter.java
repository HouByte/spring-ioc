package cn.bugio.spring.mini.rest.convert;

/**
 * @author Vincent Vic
 * @version 1.0
 * @Description 单精度转换器
 * @since 2021/1/23
 */
final class FloatConverter implements Converter<Float> {

    /**
     * 类型转换
     * 
     * @param source
     * @return
     */
    @Override
    public Float convert(Object source) {
        return Float.parseFloat(source.toString());
    }

}
