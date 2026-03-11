package com.bookingsystem.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class RoomDTO {
    // 建筑相关
    private Long buildingId;
    private String buildingName;

    // 教室类型
    private Long roomTypeId;
    private String roomTypeName;

    // 容量范围
    private Integer minCapacity;
    private Integer maxCapacity;

    //院系
    private Long departmentId;

    // 时间相关
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime startTime;

    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime endTime;

    // 分页参数
    private Integer pageNum = 1;
    private Integer pageSize = 10;
}
