package com.bookingsystem.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DayOfWeekCountVO {
    private String dayOfWeek; // 星期名称
    private Integer count;    // 预约数量
}