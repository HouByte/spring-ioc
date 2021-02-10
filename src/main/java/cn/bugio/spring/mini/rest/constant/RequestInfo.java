package cn.bugio.spring.mini.rest.constant;

import cn.bugio.spring.mini.multipart.MultipartFile;
import io.netty.handler.codec.http.FullHttpRequest;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Vincent Vic
 * @version 1.0
 * @Description 请求信息类
 * @since 2021/1/21
 */

public final class RequestInfo {
    
    private FullHttpRequest request;
    
    private HttpResponse response;
    
    private Map<String, Object> parameters = new HashMap<>();
    
    private Map<String, String> headers = new HashMap<>();
    
    private String body;
    
    private Map<String, String> formData = new HashMap<>();
    
    private List<MultipartFile> files = new ArrayList<>(8);
    
    public FullHttpRequest getRequest() {
        return this.request;
    }
    
    public void setRequest(FullHttpRequest request) {
        this.request = request;
    }
    
    public Map<String, Object> getParameters() {
        return this.parameters;
    }
    
    public Map<String, String> getHeaders() {
        return this.headers;
    }
    
    public String getBody() {
        return this.body;
    }
    public void setBody(String body) {
        this.body = body;
    }
    
    public Map<String, String> getFormData() {
        return this.formData;
    }
    
    public HttpResponse getResponse() {
        return this.response;
    }
    public void setResponse(HttpResponse response) {
        this.response = response;
    }

    public List<MultipartFile> getFiles() {
        return files;
    }

    @Override
    public String toString() {
        return "RequestInfo [request=" + request + ", response=" + response + ", parameters=" + parameters
                + ", headers=" + headers + ", body=" + body + ", formData=" + formData + ", files=" + files
                + "]";
    }
    
}
