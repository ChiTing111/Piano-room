package com.bookingsystem.aop;


import com.bookingsystem.annotation.SuperAdmin;
import com.bookingsystem.exception.BusinessException;
import com.bookingsystem.service.UserService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

/**
 * 权限检查AOP切面
 */
//@Aspect
//@Component
public class SuperAdminAspect {
    
    @Autowired
    private UserService userService;
    
    // 定义切点：所有带有RequiresPermission注解的方法
    @Pointcut("@annotation(com.bookingsystem.annotation.SuperAdmin)")
    public void permissionCheck() {}
    
    // 前置通知：在目标方法执行前进行权限检查
    @Before("permissionCheck()")
    public void doBefore(JoinPoint joinPoint) {
        // 获取当前请求
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        // 从请求中获取当前用户信息（可能从JWT或Session中获取）
        String username = (String) request.getAttribute("username");
        if (!username.equals("root")) {
            throw new BusinessException("权限不足");
        }
        
        // 获取方法上的注解
//        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
//        SuperAdmin permission = signature.getMethod().getAnnotation(SuperAdmin.class);
//
//        if (permission == null ){
//            throw new BusinessException("权限不足，需要拥有所有指定权限");
//        }
//        // 获取注解中定义的所需权限
//        String[] requiredPermissions = permission.value();
//        boolean requireAll = permission.all();
//        String permissionType = permission.type();
//
//        // 从数据库或缓存中获取用户权限
//        List<String> userPermissions = userService.getUserPermissions(username);
//
//        // 权限检查逻辑
//        if (requireAll) {
//            // 需要拥有所有权限
//            boolean hasAllPermissions = Arrays.stream(requiredPermissions)
//                    .allMatch(userPermissions::contains);
//            if (!hasAllPermissions) {
//                throw new BusinessException("权限不足，需要拥有所有指定权限");
//            }
//        } else {
//            // 只需要拥有其中一个权限
//            boolean hasAnyPermission = Arrays.stream(requiredPermissions)
//                    .anyMatch(userPermissions::contains);
//            if (!hasAnyPermission) {
//                throw new BusinessException("权限不足，需要拥有至少一个指定权限");
//            }
//        }
        
        // 权限检查通过，方法将继续执行
    }
}