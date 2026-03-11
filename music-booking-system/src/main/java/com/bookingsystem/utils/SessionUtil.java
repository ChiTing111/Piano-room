package com.bookingsystem.utils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 会话工具类
 * 用于管理HTTP会话属性，支持设置属性过期时间
 */
public class SessionUtil {

    // 存储属性过期时间的Map，key是sessionId+attributeKey，value是过期时间
    private static final ConcurrentHashMap<String, LocalDateTime> EXPIRATION_MAP = new ConcurrentHashMap<>();

    // 定时清理过期属性的调度器
    private static final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    static {
        // 启动定时任务，每分钟检查一次过期的属性并清理
        scheduler.scheduleAtFixedRate(SessionUtil::cleanExpiredAttributes, 1, 1, TimeUnit.MINUTES);
    }

    /**
     * 设置会话属性
     *
     * @param request HTTP请求对象
     * @param key 属性键
     * @param value 属性值
     */
    public static void setAttribute(HttpServletRequest request, String key, Object value) {
        HttpSession session = request.getSession();
        session.setAttribute(key, value);
    }

    /**
     * 设置会话属性，并指定过期时间
     *
     * @param request HTTP请求对象
     * @param key 属性键
     * @param value 属性值
     * @param expirationMinutes 过期时间（分钟）
     */
    public static void setAttributeWithExpiration(HttpServletRequest request, String key, Object value, int expirationMinutes) {
        HttpSession session = request.getSession();

        // 设置属性
        session.setAttribute(key, value);

        // 计算过期时间
        LocalDateTime expirationTime = LocalDateTime.now().plusMinutes(expirationMinutes);

        // 保存过期时间
        String mapKey = buildExpirationKey(session.getId(), key);
        EXPIRATION_MAP.put(mapKey, expirationTime);
    }

    /**
     * 检查属性是否已过期
     *
     * @param request HTTP请求对象
     * @param key 属性键
     * @return 如果过期返回true，否则返回false
     */
    public static boolean isAttributeExpired(HttpServletRequest request, String key) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return true;
        }

        String mapKey = buildExpirationKey(session.getId(), key);
        LocalDateTime expirationTime = EXPIRATION_MAP.get(mapKey);

        // 如果没有设置过期时间，视为不过期
        if (expirationTime == null) {
            return false;
        }

        // 判断是否过期
        boolean expired = LocalDateTime.now().isAfter(expirationTime);

        // 如果已过期，移除属性和过期信息
        if (expired) {
            session.removeAttribute(key);
            EXPIRATION_MAP.remove(mapKey);
        }

        return expired;
    }

    /**
     * 获取会话属性（已检查过期时间）
     *
     * @param request HTTP请求对象
     * @param key 属性键
     * @return 属性值，如果不存在或已过期则返回null
     */
    public static Object getAttribute(HttpServletRequest request, String key) {
        HttpSession session = request.getSession();

        // 检查属性是否过期
        if (isAttributeExpired(request, key)) {
            return null;
        }

        return session.getAttribute(key);
    }

    /**
     * 移除会话属性
     *
     * @param request HTTP请求对象
     * @param key 属性键
     */
    public static void removeAttribute(HttpServletRequest request, String key) {
        HttpSession session = request.getSession();
        session.removeAttribute(key);

        // 同时移除过期信息
        String mapKey = buildExpirationKey(session.getId(), key);
        EXPIRATION_MAP.remove(mapKey);
    }

    /**
     * 获取当前会话ID
     *
     * @param request HTTP请求对象
     * @return 会话ID
     */
    public static String getSessionId(HttpServletRequest request) {
        HttpSession session = request.getSession();
        return session.getId();
    }

    /**
     * 获取当前会话对象
     *
     * @param request HTTP请求对象
     * @return HttpSession对象
     */
    public static HttpSession getCurrentSession(HttpServletRequest request) {
        return request.getSession();
    }

    /**
     * 刷新属性过期时间
     *
     * @param request HTTP请求对象
     * @param key 属性键
     * @param expirationMinutes 新的过期时间（分钟）
     * @return 如果属性存在且成功刷新返回true，否则返回false
     */
    public static boolean refreshAttributeExpiration(HttpServletRequest request, String key, int expirationMinutes) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return false;
        }

        // 检查属性是否存在
        if (session.getAttribute(key) == null) {
            return false;
        }

        // 更新过期时间
        LocalDateTime expirationTime = LocalDateTime.now().plusMinutes(expirationMinutes);
        String mapKey = buildExpirationKey(session.getId(), key);
        EXPIRATION_MAP.put(mapKey, expirationTime);

        return true;
    }

    /**
     * 获取属性剩余有效时间（分钟）
     *
     * @param request HTTP请求对象
     * @param key 属性键
     * @return 剩余分钟数，如果属性不存在或已过期则返回0
     */
    public static long getRemainingMinutes(HttpServletRequest request, String key) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return 0;
        }

        String mapKey = buildExpirationKey(session.getId(), key);
        LocalDateTime expirationTime = EXPIRATION_MAP.get(mapKey);

        // 如果没有设置过期时间，返回0
        if (expirationTime == null) {
            return 0;
        }

        // 如果已过期，返回0
        LocalDateTime now = LocalDateTime.now();
        if (now.isAfter(expirationTime)) {
            return 0;
        }

        // 计算剩余分钟数
        long seconds = java.time.Duration.between(now, expirationTime).getSeconds();
        return seconds / 60;
    }

    /**
     * 获取某个会话中所有设置了过期时间的属性键
     *
     * @param request HTTP请求对象
     * @return 属性键数组
     */
    public static String[] getAttributesWithExpiration(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return new String[0];
        }

        String sessionIdPrefix = session.getId() + ":";
        return EXPIRATION_MAP.keySet().stream()
                .filter(key -> key.startsWith(sessionIdPrefix))
                .map(key -> key.substring(sessionIdPrefix.length()))
                .toArray(String[]::new);
    }

    /**
     * 清除会话中所有过期属性
     *
     * @param request HTTP请求对象
     * @return 清除的属性数量
     */
    public static int clearExpiredAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return 0;
        }

        String sessionId = session.getId();
        LocalDateTime now = LocalDateTime.now();
        int count = 0;

        for (String key : EXPIRATION_MAP.keySet()) {
            if (key.startsWith(sessionId + ":")) {
                LocalDateTime expirationTime = EXPIRATION_MAP.get(key);
                if (expirationTime != null && now.isAfter(expirationTime)) {
                    String attributeKey = key.substring(sessionId.length() + 1);
                    session.removeAttribute(attributeKey);
                    EXPIRATION_MAP.remove(key);
                    count++;
                }
            }
        }

        return count;
    }

    /**
     * 构建过期Map的键
     */
    private static String buildExpirationKey(String sessionId, String attributeKey) {
        return sessionId + ":" + attributeKey;
    }

    /**
     * 清理过期的属性
     * 由定时任务调用，从所有活跃会话中移除过期的属性
     */
    private static void cleanExpiredAttributes() {
        LocalDateTime now = LocalDateTime.now();

        // 使用迭代器安全删除
        EXPIRATION_MAP.entrySet().removeIf(entry -> now.isAfter(entry.getValue()));
    }

    /**
     * 关闭资源
     * 应用关闭时调用
     */
    public static void shutdown() {
        try {
            scheduler.shutdown();
            if (!scheduler.awaitTermination(5, TimeUnit.SECONDS)) {
                scheduler.shutdownNow();
            }
        } catch (InterruptedException e) {
            scheduler.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}