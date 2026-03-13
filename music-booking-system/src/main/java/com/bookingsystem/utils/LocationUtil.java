package com.bookingsystem.utils;

import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 位置工具类
 * 用于计算两点之间的距离，验证签到位置
 */
@Slf4j
public class LocationUtil {

    // 地球半径（米）
    private static final double EARTH_RADIUS = 6371000;

    /**
     * 计算两点之间的距离（使用Haversine公式）
     *
     * @param lat1 点1纬度
     * @param lng1 点1经度
     * @param lat2 点2纬度
     * @param lng2 点2经度
     * @return 距离（米）
     */
    public static double calculateDistance(double lat1, double lng1, double lat2, double lng2) {
        double radLat1 = Math.toRadians(lat1);
        double radLat2 = Math.toRadians(lat2);
        double a = radLat1 - radLat2;
        double b = Math.toRadians(lng1) - Math.toRadians(lng2);

        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) +
                Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
        s = s * EARTH_RADIUS;
        return Math.round(s * 100) / 100.0; // 保留两位小数
    }

    /**
     * 计算两点之间的距离（BigDecimal版本）
     */
    public static double calculateDistance(BigDecimal lat1, BigDecimal lng1, BigDecimal lat2, BigDecimal lng2) {
        if (lat1 == null || lng1 == null || lat2 == null || lng2 == null) {
            return -1;
        }
        return calculateDistance(
                lat1.doubleValue(),
                lng1.doubleValue(),
                lat2.doubleValue(),
                lng2.doubleValue()
        );
    }

    /**
     * 验证签到位置是否在允许范围内
     *
     * @param roomLat    琴房纬度
     * @param roomLng    琴房经度
     * @param userLat    用户纬度
     * @param userLng    用户经度
     * @param radius     允许半径（米）
     * @return 是否在范围内
     */
    public static boolean isWithinRange(BigDecimal roomLat, BigDecimal roomLng,
                                        BigDecimal userLat, BigDecimal userLng,
                                        Integer radius) {
        if (roomLat == null || roomLng == null || userLat == null || userLng == null) {
            log.warn("位置信息不完整，无法验证距离");
            return false;
        }

        double distance = calculateDistance(roomLat, roomLng, userLat, userLng);
        log.info("用户与琴房距离：{}米，允许范围：{}米", distance, radius);

        return distance <= radius;
    }

    /**
     * 格式化距离显示
     */
    public static String formatDistance(double distance) {
        if (distance < 1000) {
            return String.format("%.0f米", distance);
        } else {
            return String.format("%.2f公里", distance / 1000);
        }
    }
}
