package com.bookingsystem.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Room {
    private Long id;
    private String roomNumber;
    private String name;
    private Long buildingId;
    private Integer floor;
    private Integer capacity;
    private Long roomTypeId;
    private String facilities;
    private Integer status;
    private String description;
    private String seats;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    /**
     * 新增部门id和部门名称
     */
    private Long departmentId;
    private String departmentName;


 }