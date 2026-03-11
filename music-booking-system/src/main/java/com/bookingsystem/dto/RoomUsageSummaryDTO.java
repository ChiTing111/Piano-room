package com.bookingsystem.dto;

import lombok.Data;

@Data
public class RoomUsageSummaryDTO {
    private Integer occupiedCount; // 已预约数量
    private Integer freeCount; // 空闲数量
    private Double occupiedRate; // 已预约占比
    private Double freeRate; // 空闲占比
}
