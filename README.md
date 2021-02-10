# spring-mini
基于Netty实现的高性能RESTful服务框架

## 主要注解
注解名称参考了Spring MVC，编译理解和记忆

- @RestController
- @RequestMapping
- @GetMapping
- @PostMapping
- @DeleteMapping
- @PutMapping
- @PatchMapping
- @JsonResponse
- @RequestParam
- @PathVariable
- @RequestBody
- @UploadFile
- @UrlEncodedForm
- @RequestHeader

## controller示例：
```java
//默认为单例，singleton = false表示启用多例。
//@RestController(singleton = false)
@RestController
@RequestMapping("/users")
public class UserController {
    
    @GetMapping("")
    @JsonResponse
    public ResponseEntity<User> listUser() {
        // 查询用户
        User user = new User();
        user.setId(1);
        user.setName("Leo");
        user.setAge((short)18);
        return ResponseEntity.ok().build(user);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> putMethod(@PathVariable("id") int id, @RequestBody String body) {
        // 更新用户
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMethod(@PathVariable int id) {
        // 删除用户
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    
    @PostMapping("")
    public ResponseEntity<?> postMethod(@RequestBody String body) {
        // 添加用户
        JSONObject json = JSONObject.parseObject(body);
        User user = new User();
        user.setId(json.getIntValue("id"));
        user.setName(json.getString("name"));
        user.setAge(json.getShortValue("age"));
        return ResponseEntity.status(HttpStatus.CREATED).build(user);
    }

}
```

## 拦截器示例：跨域控制器
```java
public final class CorsInterceptor implements Interceptor {

    @Override
    public boolean preHandle(FullHttpRequest request, HttpResponse response) throws Exception {
        // 使用axios发送cookie，这里不能用*，需要使用Web前端地址，如：http://localhost:8080
        // response.getHeaders().put("Access-Control-Allow-Origin", "*");
        response.getHeaders().put("Access-Control-Allow-Origin", System.getProperty("http.origin"));
        response.getHeaders().put("Access-Control-Allow-Methods", "POST, PUT, GET, OPTIONS, DELETE, PATCH");
        response.getHeaders().put("Access-Control-Max-Age", "3600");
        response.getHeaders().put("Access-Control-Allow-Headers", "Content-Type,X-Token");
        response.getHeaders().put("Access-Control-Allow-Credentials", "true");
        return true;
    }

    @Override
    public void postHandle(FullHttpRequest request, HttpResponse response) throws Exception {
    }

    @Override
    public void afterCompletion(FullHttpRequest request, HttpResponse response) {
    }

}
```
## 配置
```yaml
server:
  port: 9696   #端口号
  maxContentLength: 1024
  controllerBasePackage: cn.bugio.server
```
## 启动服务
```java
@ComponentScan("cn.bugio.spring.demo.service")
public class Test {

    public static void main(String[] args) {

        SpringMiniBootApplication.run(Test.class,args);
    }
}
```

## 访问服务
http://localhost:8080/服务名称



