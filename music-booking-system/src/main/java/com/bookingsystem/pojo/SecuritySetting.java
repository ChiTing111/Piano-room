package com.bookingsystem.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 安全设置实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SecuritySetting implements Serializable {

    /**
     * Token过期时间（小时，默认24小时）
     */
    private Integer tokenExpireHours = 24;

    /**
     * 最小密码长度（默认6位）
     */
    private Integer minPasswordLength = 6;
}
