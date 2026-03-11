// 1. 创建拦截器类 - JwtAuthInterceptor.java
package com.bookingsystem.interceptor;

import com.bookingsystem.exception.BusinessException;
import com.bookingsystem.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * JWT认证拦截器
 * 用于拦截需要认证的接口，验证JWT令牌
 */
@Slf4j
@Component
public class JwtAuthInterceptor implements HandlerInterceptor {


    @Autowired
    private  JwtUtil jwtUtil;
    
    // 在请求处理之前执行
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // 获取请求头中的token
        String token = request.getHeader("Authorization");
        
        // 如果没有token或格式不正确
        if (!StringUtils.hasText(token) || !token.startsWith("Bearer ")) {
            throw new BusinessException(401,"未登录或token已过期");
        }
        
        // 截取Bearer 后的token部分
        token = token.substring(7);
        
        try {
            // 验证token
            String username = jwtUtil.extractUsername(token);
            if (username == null || jwtUtil.isTokenExpired(token)) {
                throw new BusinessException(401,"未登录或token已过期");
            }
            
            // 将用户信息存入请求属性，方便后续使用
            request.setAttribute("username", username);
            
            return true; // 放行请求
        } catch (Exception e) {
            log.error("Token验证失败", e);
            throw new BusinessException(401,"未登录或token已过期");
        }
    }
}
