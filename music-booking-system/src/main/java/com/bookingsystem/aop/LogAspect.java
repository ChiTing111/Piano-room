package com.bookingsystem.aop;

import com.bookingsystem.annotation.Log;
import com.bookingsystem.pojo.OperationLog;
import com.bookingsystem.service.OperationLogService;
import com.bookingsystem.utils.IpUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.LocalDateTime;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class LogAspect {

    private final OperationLogService operationLogService;
    private final ObjectMapper objectMapper;

    @Pointcut("@annotation(com.bookingsystem.annotation.Log)")
    public void logPointCut() {}

    @AfterReturning(value = "logPointCut()", returning = "result")
    public void afterReturning(JoinPoint joinPoint, Object result) {
        handleLog(joinPoint, result, null);
    }

    @AfterThrowing(value = "logPointCut()", throwing = "e")
    public void afterThrowing(JoinPoint joinPoint, Throwable e) {
        handleLog(joinPoint, null, e);
    }

    private void handleLog(JoinPoint joinPoint, Object result, Throwable exception) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Log logAnnotation = signature.getMethod().getAnnotation(Log.class);

        OperationLog logEntry = new OperationLog();
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        
        try {
            //TODO 获取当前登录用户名
            logEntry.setUsername((String) request.getAttribute("username"));
            logEntry.setOperationModule(logAnnotation.module());
            logEntry.setOperationType(logAnnotation.type());
            logEntry.setOperationDesc(logAnnotation.description());
            logEntry.setRequestUrl(request.getRequestURI());
            logEntry.setRequestMethod(request.getMethod());
            logEntry.setRequestIp(IpUtils.getIpAddr(request));
            logEntry.setRequestParam(objectMapper.writeValueAsString(joinPoint.getArgs()));
            if (exception == null) {
                logEntry.setStatus(1);
                logEntry.setResponseResult(objectMapper.writeValueAsString(result));
            } else {
                logEntry.setStatus(0);
                logEntry.setErrorMsg(exception.getMessage());
            }
            logEntry.setCreatedAt(LocalDateTime.now());

            operationLogService.save(logEntry);
        } catch (Exception e) {
            log.error("记录操作日志失败", e);
        }
    }
}
