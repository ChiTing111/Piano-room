package com.bookingsystem.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

/**
 * JSON工具类，基于Jackson
 */
public class JsonUtils {
    private static final Logger logger = LoggerFactory.getLogger(JsonUtils.class);
    
    // 默认日期格式
    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    
    // 创建ObjectMapper实例并进行配置
    private static final ObjectMapper MAPPER = new ObjectMapper();
    
    // 创建包含空值的ObjectMapper实例
    private static final ObjectMapper INCLUDE_NULL_MAPPER = new ObjectMapper();
    
    static {
        // 配置默认ObjectMapper (不包含空值)
        MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        configureCommon(MAPPER);
        
        // 配置包含空值的ObjectMapper
        INCLUDE_NULL_MAPPER.setSerializationInclusion(JsonInclude.Include.ALWAYS);
        configureCommon(INCLUDE_NULL_MAPPER);
    }
    
    // 为ObjectMapper配置通用设置
    private static void configureCommon(ObjectMapper mapper) {
        // 设置日期格式
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        mapper.setDateFormat(dateFormat);
        mapper.setTimeZone(TimeZone.getDefault());
        
        // 禁用将日期序列化为时间戳
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        
        // 忽略未知属性
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        
        // 允许空对象
        mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
    }
    
    /**
     * 将对象转换为JSON字符串（默认不包含空值）
     * @param obj 要转换的对象
     * @return JSON字符串
     */
    public static String toJson(Object obj) {
        try {
            return MAPPER.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            logger.error("转换对象到JSON失败", e);
            throw new RuntimeException("转换对象到JSON失败", e);
        }
    }
    
    /**
     * 将对象转换为JSON字符串（包含空值）
     * @param obj 要转换的对象
     * @return JSON字符串
     */
    public static String toJsonWithNulls(Object obj) {
        try {
            return INCLUDE_NULL_MAPPER.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            logger.error("转换对象到JSON失败", e);
            throw new RuntimeException("转换对象到JSON失败", e);
        }
    }
    
    /**
     * 将对象转换为格式化的JSON字符串（默认不包含空值）
     * @param obj 要转换的对象
     * @return 美化后的JSON字符串
     */
    public static String toPrettyJson(Object obj) {
        try {
            return MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            logger.error("转换对象到格式化JSON失败", e);
            throw new RuntimeException("转换对象到格式化JSON失败", e);
        }
    }
    
    /**
     * 将对象转换为格式化的JSON字符串（包含空值）
     * @param obj 要转换的对象
     * @return 美化后的JSON字符串
     */
    public static String toPrettyJsonWithNulls(Object obj) {
        try {
            return INCLUDE_NULL_MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            logger.error("转换对象到格式化JSON失败", e);
            throw new RuntimeException("转换对象到格式化JSON失败", e);
        }
    }
    
    /**
     * 将JSON字符串转换为对象
     * @param jsonString JSON字符串
     * @param clazz 目标类
     * @param <T> 泛型类型
     * @return 转换后的对象
     */
    public static <T> T parseObject(String jsonString, Class<T> clazz) {
        try {
            return MAPPER.readValue(jsonString, clazz);
        } catch (IOException e) {
            logger.error("解析JSON到对象失败", e);
            throw new RuntimeException("解析JSON到对象失败", e);
        }
    }
    
    /**
     * 将JSON字符串转换为复杂类型对象
     * @param jsonString JSON字符串
     * @param typeReference 类型引用
     * @param <T> 泛型类型
     * @return 转换后的对象
     */
    public static <T> T parseObject(String jsonString, TypeReference<T> typeReference) {
        try {
            return MAPPER.readValue(jsonString, typeReference);
        } catch (IOException e) {
            logger.error("解析JSON到复杂对象失败", e);
            throw new RuntimeException("解析JSON到复杂对象失败", e);
        }
    }
    
    /**
     * 将JSON字符串转换为对象列表
     * @param jsonString JSON字符串
     * @param clazz 目标类
     * @param <T> 泛型类型
     * @return 对象列表
     */
    public static <T> List<T> parseArray(String jsonString, Class<T> clazz) {
        try {
            JavaType javaType = MAPPER.getTypeFactory().constructParametricType(List.class, clazz);
            return MAPPER.readValue(jsonString, javaType);
        } catch (IOException e) {
            logger.error("解析JSON到列表失败", e);
            throw new RuntimeException("解析JSON到列表失败", e);
        }
    }
    
    /**
     * 将JSON字符串转换为Map
     * @param jsonString JSON字符串
     * @return Map对象
     */
    public static Map<String, Object> parseMap(String jsonString) {
        try {
            return MAPPER.readValue(jsonString, new TypeReference<Map<String, Object>>() {});
        } catch (IOException e) {
            logger.error("解析JSON到Map失败", e);
            throw new RuntimeException("解析JSON到Map失败", e);
        }
    }
    
    /**
     * 将对象转换为字节数组
     * @param obj 要转换的对象
     * @return 字节数组
     */
    public static byte[] toBytes(Object obj) {
        try {
            return MAPPER.writeValueAsBytes(obj);
        } catch (JsonProcessingException e) {
            logger.error("转换对象到字节数组失败", e);
            throw new RuntimeException("转换对象到字节数组失败", e);
        }
    }
    
    /**
     * 判断字符串是否为有效的JSON格式
     * @param jsonString 要检查的字符串
     * @return 是否为有效JSON
     */
    public static boolean isValidJson(String jsonString) {
        try {
            MAPPER.readTree(jsonString);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}