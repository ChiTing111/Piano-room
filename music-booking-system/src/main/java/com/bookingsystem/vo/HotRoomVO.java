package com.bookingsystem.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HotRoomVO {
    private Long roomId;
    private String roomName;
    private String buildingName;
    private Integer reservationCount;
}
