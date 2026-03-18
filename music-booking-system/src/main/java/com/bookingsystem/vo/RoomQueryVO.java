package com.bookingsystem.vo;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class RoomQueryVO {
    private Long id;
    private String roomNumber; // 教室编号
    private String name; // 教室名称
    private Integer floor; // 所在楼层
    private Integer capacity; // 教室容量
    private Long roomTypeId; // 教室类型ID
    private String facilities; // 教室设施
    private Integer status; // 教室状态
    private String description; // 教室描述
    private BigDecimal longitude; // 经度
    private BigDecimal latitude; // 纬度
    private Integer checkInRadius; // 签到允许半径（米）
    private LocalDateTime createdAt; // 创建时间
    private LocalDateTime updatedAt; // 更新时间
    
    // 添加字段表示是否设置了地理位置信息
    private Boolean hasLocationInfo; // 是否设置了地理位置信息
}
