package com.bookingsystem.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Room {
    private Long id;
    private String roomNumber;
    private String name;
    private Integer floor;
    private Integer capacity;
    private Long roomTypeId;
    private String facilities;
    private Integer status;
    private String description;
    private BigDecimal longitude;  // 经度
    private BigDecimal latitude;   // 纬度
    private Integer checkInRadius; // 签到允许半径（米）
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    // 添加地理位置信息状态字段
    private Boolean hasLocationInfo; // 是否设置了地理位置信息
}