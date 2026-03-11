package com.bookingsystem.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 阶梯惩罚规则实体
 * 对应数据库 penalty_rules 表
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PenaltyRule {
    private Long id;
    /** 触发此规则的违约次数阈值 */
    private Integer violationCount;
    /** 封禁天数（0=仅警告） */
    private Integer banDays;
    /** 规则说明 */
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
