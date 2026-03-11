package com.bookingsystem.dto;

import lombok.Data;

@Data
public class RoomTypeUsageDTO {
    private String typeName;
    private Integer totalRooms;
    private Integer occupiedCount;
    private Integer freeCount;
    private Double occupiedRate;
    private Double freeRate;
}
