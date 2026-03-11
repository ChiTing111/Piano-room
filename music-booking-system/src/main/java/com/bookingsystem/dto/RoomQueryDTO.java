package com.bookingsystem.dto;

import lombok.Data;

@Data
public class RoomQueryDTO {
    private Integer  id;
    private Long buildingId; //教学楼
    private Integer status; //0:维护中 1:可用
    private String roomTypeId; //教室类型
    private String roomNumberOrName; //教室号或名称
    private Integer page = 1; //默认第一页
    private Integer pageSize = 10; //默认每页10条数据
}
