package com.bookingsystem.vo;

import lombok.Data;

@Data
public class RoomQueryVO {
    private Long id;
    private String roomNumber; // 教室编号
    private String name; // 教室名称
    private Long roomTypeId; // 教室类型
    private String type; // 教室类型名称
    private String facilities; // 教室设施
    private Integer capacity; // 教室容量
    private Integer status; // 教室状态
    private String buildingName; // 教室所属楼栋
    private String seats; // 教室座位数
    /**
     * 新增教室关联部门
     */
    private Long departmentId; // 部门id
    private String departmentName; // 部门名称

}
