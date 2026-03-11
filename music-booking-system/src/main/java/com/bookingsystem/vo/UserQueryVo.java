package com.bookingsystem.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserQueryVo {
    private Long id;
    private String username; // 用户名
    private String realName; // 真实姓名
    private String studentId; // 学号
    private String userType; // 用户类型
    private String email; // 邮箱
    private Integer status; // 状态
    private String phone;
    private String grade; // 年级
    private String major; // 专业
    private Long departmentId; // 院系id
    private String departmentName; // 院系名称
    private String avatarUrl;
    private LocalDateTime lastLoginTime;
    private LocalDateTime createdAt;

    /**
     * 拓展字段 预约次数
     */
    private Integer reservationCount;

    /**
     * 拓展字段 取消次数
     */
    private Integer cancelCount;

    /**
     * 违约次数
     */
    private Integer violationCount;

    /**
     * 封禁到期时间
     */
    private LocalDateTime banUntil;
}
