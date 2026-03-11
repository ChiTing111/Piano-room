package com.bookingsystem.config;



import com.bookingsystem.interceptor.JwtAuthInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web配置类，用于注册拦截器等Web相关配置
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private  JwtAuthInterceptor jwtAuthInterceptor;
    
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册JWT认证拦截器
        registry.addInterceptor(jwtAuthInterceptor)
                // 添加需要拦截的路径
                .addPathPatterns("/**")  // 拦截所有/开头的请求
                // 排除不需要拦截的路径
                .excludePathPatterns(
                        "/user/login",       // 登录接口
                        "/user/register",    // 注册接口
                        "/user/captcha",     // 验证码接口
                        "/user/email/code",
                      "/system/settings/basic",
                        "/announcements",
                        "/classType",
                        "/reports/classroomUsageRate",
                        "/reports/classroomDistribution",
                        "/room/hot-today",
                        "/depts",
                        "/classType/all",
                        "/buildings",
                        "/room/search",
                        "/reservations/availability",
                        "/reservations/list",
                        "/room/*"


                );
    }
}