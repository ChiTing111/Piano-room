package com.bookingsystem.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 功能：
 * 作者： lapis
 * 邮箱： lapis17@qq.com
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoVO {
    private Long id;
    private String username;
    private String realName;
    private String avatarUrl;
    private String studentId;
    private String email;
    private String phone;
    private String departmentName;
    private String userType;
    private Integer violationCount;
    private java.time.LocalDateTime banUntil;

}
