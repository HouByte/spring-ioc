package cn.bugio.spring.mini.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;

/**
 * @author Vincent Vic
 * @version 1.0
 * @Description 配置文件对应下拉集合显示
 * @since 2021/1/26 20:48
 */

public class YamlUtil {


    private static String yamlPath = "application.yml"; // yaml路径

    private static boolean enable = true;



    private static HashMap hashMap;

    // 计划审核表-查询类型（计划审核、配合审核）
    public static final String TCX_JHSBB_AUDIT_SEARCHSBLX = "TCX_JHSBB_AUDIT_searchSblx"; // 查询类型

    static {
        Yaml yaml = new Yaml();
        String file = YamlUtil.class.getClassLoader().getResource(yamlPath).getFile();
        if (file != null){
            File yamlFile = new File(file);
            if (!yamlFile.exists()){
                System.out.println("111");
            }
            try {
                hashMap = yaml.loadAs(new FileInputStream(yamlFile), HashMap.class);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            enable = false;
        }

    }

    public static void setYamlPath(String yamlPath) {
        YamlUtil.yamlPath = yamlPath;
        Yaml yaml = new Yaml();
        String file = YamlUtil.class.getClassLoader().getResource(yamlPath).getFile();
        if (file != null){
            File yamlFile = new File(file);
            if (!yamlFile.exists()){
                System.out.println("111");
            }
            try {
                hashMap = yaml.loadAs(new FileInputStream(yamlFile), HashMap.class);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            enable = false;
        }
    }

    /**
     * 读取下拉状态配置参数List
     * @param typeName
     * @return
     * @throws IOException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     */
    public static List<Object> getTypePropertie(String typeName) throws IOException{
        String[] findTypeName = typeName.split("\\.");
        if (findTypeName.length == 0){
            return (List<Object>) hashMap.get(typeName);
        }
        HashMap map = hashMap;
        for (int i = 0; i < findTypeName.length - 1; i++) {

            Object o = map.get(findTypeName[i]);
            if (o instanceof Map){
                map = (HashMap) o;
            } else {
                return null;
            }
        }
        return (List)map.get(findTypeName[findTypeName.length-1]);
    }

    /**
     * 读取下拉状态配置参数返回map
     * @param typeName
     * @return
     * @throws IOException
     */
    public static Map<String,Object> getTypePropertieMap(String typeName) throws IOException{
        String[] findTypeName = typeName.split("\\.");
        if (findTypeName.length == 0){
            return (Map<String,Object>) hashMap.get(typeName);
        }
        HashMap map = hashMap;
        for (int i = 0; i < findTypeName.length - 1; i++) {

            Object o = map.get(findTypeName[i]);
            if (o instanceof Map){
                map = (HashMap) o;
            } else {
                return null;
            }
        }
        return (Map<String, Object>) map.get(findTypeName[findTypeName.length-1]);
    }

    public static boolean isEnable() {
        return enable;
    }
}
