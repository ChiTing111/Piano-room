package com.bookingsystem.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 预约设置实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservationSetting implements Serializable {

    /**
     * 最大提前预约天数（默认7天）
     */
    private Integer maxAdvanceDays = 7;

    /**
     * 签到宽限时间（分钟，默认10分钟）
     */
    private Integer signInGrace = 10;

    /**
     * 最大违约次数（默认3次）
     */
    private Integer maxNoShow = 3;
}
