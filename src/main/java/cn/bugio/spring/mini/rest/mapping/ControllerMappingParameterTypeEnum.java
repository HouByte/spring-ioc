package cn.bugio.spring.mini.rest.mapping;


/**
 * @author Vincent Vic
 * @version 1.0
 * @Description 请求映射参数类型枚举
 * @since 2021/1/23
 */

public enum ControllerMappingParameterTypeEnum {
    
    /**
     * Request Url 参数
     */
    REQUEST_PARAM,
    
    /**
     * 路径变量
     */
    PATH_VARIABLE,
    
    /**
     * Http Request
     */
    HTTP_REQUEST,
    
    /**
     * Http Response
     */
    HTTP_RESPONSE,
    
    /**
     * 请求体
     */
    REQUEST_BODY,
    
    /**
     * X-WWW-FORM-URLENCODED
     */
    URL_ENCODED_FORM,
    
    /**
     * Http Reqesut Header参数
     */
    REQUEST_HEADER,
    
    /**
     * 上传文件
     */
    UPLOAD_FILE,
    
    /**
     * 多个上传文件
     */
    UPLOAD_FILES

}
