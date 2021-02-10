package cn.bugio.spring.mini.rest.convert;

import java.util.Date;

/**
 * @author Vincent Vic
 * @version 1.0
 * @Description 转换器工厂类
 * @since 2021/1/23
 */
public final class ConverterFactory {

    /**
     * 创建转换器
     * @param clazz
     * @return
     */
    public static Converter<?> create(Class<?> clazz) {
        if (clazz.equals(String.class)) {
            return new StringConverter();
        }
        if (clazz.equals(int.class) || clazz.equals(Integer.class)) {
            return new IntegerConverter();
        }
        if (clazz.equals(long.class) || clazz.equals(Long.class)) {
            return new LongConverter();
        }
        if (clazz.equals(float.class) || clazz.equals(Float.class)) {
            return new FloatConverter();
        }
        if (clazz.equals(double.class) || clazz.equals(Double.class)) {
            return new DoubleConverter();
        }
        if (clazz.equals(Date.class)) {
            return new DateConverter();
        }
        return null;
    }

}
