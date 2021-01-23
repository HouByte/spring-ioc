package cn.bugio.spring.mini.controller;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Vincent Vic
 * @version 1.0
 * @Description
 * @since 2021/1/20 21:10
 */


public class ParameterNameUtil {

    public static void main(String[] args) {
        List<String> paramterNames = getParameterNameJava8(
                ParameterNameUtil.class, "getParameterNameJava8");
        paramterNames.forEach((x) -> System.out.println(x));
    }

    public static List<String> getParameterNameJava8(Class clazz, String methodName) {
        List<String> paramterList = new ArrayList<>();
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            if (methodName.equals(method.getName())) {
                //直接通过method就能拿到所有的参数
                Parameter[] params = method.getParameters();
                for (Parameter parameter : params) {
                    paramterList.add(parameter.getName());
                }
            }
        }
        return paramterList;
    }
}