package com.bookingsystem.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * 处理嵌套JSON的工具类
 */
public class NestedJsonUtils {
    private static final Logger logger = LoggerFactory.getLogger(NestedJsonUtils.class);
    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 解析嵌套的JSON响应，data字段是JSON字符串
     * @param jsonResponse 响应JSON字符串
     * @return 完全解析后的对象
     */
    public static <T> ApiResponse<T> parseNestedJson(String jsonResponse, Class<T> dataClass) {
        try {
            // 解析外层JSON
            JsonNode rootNode = objectMapper.readTree(jsonResponse);
            
            // 获取基本字段
            int code = rootNode.get("code").asInt();
            String msg = rootNode.get("msg").asText();
            
            // 获取并解析data字段中的JSON字符串
            if (rootNode.has("data") && !rootNode.get("data").isNull()) {
                String dataStr = rootNode.get("data").asText();
                // 解析data字段的JSON字符串为目标类型
                T data = objectMapper.readValue(dataStr, dataClass);
                return new ApiResponse<>(code, msg, data);
            } else {
                return new ApiResponse<>(code, msg, null);
            }
        } catch (Exception e) {
            logger.error("解析嵌套JSON失败", e);
            throw new RuntimeException("解析嵌套JSON失败: " + e.getMessage(), e);
        }
    }
    
    /**
     * 解析嵌套的JSON响应，data字段是JSON字符串，转换为Map
     * @param jsonResponse 响应JSON字符串
     * @return 包含解析后data的响应对象
     */
    public static ApiResponse<Map<String, Object>> parseNestedJsonToMap(String jsonResponse) {
        try {
            // 解析外层JSON
            JsonNode rootNode = objectMapper.readTree(jsonResponse);
            
            // 获取基本字段
            int code = rootNode.get("code").asInt();
            String msg = rootNode.get("msg").asText();
            
            // 获取并解析data字段中的JSON字符串
            if (rootNode.has("data") && !rootNode.get("data").isNull()) {
                String dataStr = rootNode.get("data").asText();
                // 解析data字段的JSON字符串为Map
                Map<String, Object> dataMap = objectMapper.readValue(dataStr, 
                    new TypeReference<Map<String, Object>>() {});
                return new ApiResponse<>(code, msg, dataMap);
            } else {
                return new ApiResponse<>(code, msg, null);
            }
        } catch (Exception e) {
            logger.error("解析嵌套JSON到Map失败", e);
            throw new RuntimeException("解析嵌套JSON到Map失败: " + e.getMessage(), e);
        }
    }
    
    /**
     * 将对象转换为JSON字符串
     * @param obj 要转换的对象
     * @return JSON字符串
     */
    public static String toJson(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            logger.error("对象转JSON失败", e);
            throw new RuntimeException("对象转JSON失败", e);
        }
    }
    
    /**
     * API响应包装类
     * @param <T> 数据类型
     */
    public static class ApiResponse<T> {
        private int code;
        private String msg;
        private T data;
        
        public ApiResponse(int code, String msg, T data) {
            this.code = code;
            this.msg = msg;
            this.data = data;
        }
        
        public int getCode() {
            return code;
        }
        
        public String getMsg() {
            return msg;
        }
        
        public T getData() {
            return data;
        }
        
        @Override
        public String toString() {
            return "ApiResponse{" +
                   "code=" + code +
                   ", msg='" + msg + '\'' +
                   ", data=" + data +
                   '}';
        }
    }
}