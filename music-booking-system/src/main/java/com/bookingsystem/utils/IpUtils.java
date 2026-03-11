package com.bookingsystem.utils;

import jakarta.servlet.http.HttpServletRequest;


public class IpUtils {

    /**
     * 获取客户端真实 IP 地址，兼容多级代理
     */
    public static String getClientIp(HttpServletRequest request) {
        String[] headersToCheck = {
            "X-Forwarded-For",
            "Proxy-Client-IP",
            "WL-Proxy-Client-IP",
            "HTTP_CLIENT_IP",
            "HTTP_X_FORWARDED_FOR"
        };

        for (String header : headersToCheck) {
            String ip = request.getHeader(header);
            if (ip != null && ip.length() > 0 && !"unknown".equalsIgnoreCase(ip)) {
                // 如果存在多个 IP（比如 X-Forwarded-For: client, proxy1, proxy2），取第一个
                return ip.split(",")[0].trim();
            }
        }

        // 如果没有代理，直接获取远程地址
        return request.getRemoteAddr();
    }

    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if (ip != null && ip.length() > 15) {
            // 多个IP，取第一个
            ip = ip.split(",")[0];
        }
        return ip;
    }
}
