package cn.bugio.spring.mini.rest.convert;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Vincent Vic
 * @version 1.0
 * @Description 日期转换器
 * @since 2021/1/23
 */
final class DateConverter implements Converter<Date> {

    /**
     * 类型转换
     * 
     * @param source
     * @return
     */
    @Override
    public Date convert(Object source) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return sdf.parse(source.toString());
        } catch (ParseException e) {
            return null;
        }
    }

}
