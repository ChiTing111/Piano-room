package com.bookingsystem.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserReservationStatsVO {
    private Integer totalCount;        // 总预约次数
    private Integer completedCount;    // 已完成
    private Integer approvedCount;     // 进行中（已预约）
    private Integer cancelledCount;    // 已取消
    private Integer occupiedCount;     // 违约次数
}