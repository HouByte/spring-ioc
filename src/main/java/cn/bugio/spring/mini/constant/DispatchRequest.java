package cn.bugio.spring.mini.constant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @author Vincent Vic
 * @version 1.0
 * @Description
 * @since 2021/1/18
 */

@NoArgsConstructor
@AllArgsConstructor
@Data
public class DispatchRequest {

    String uri;
    String contextPath;
    Map<String,String[]> params;



}
