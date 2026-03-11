package com.bookingsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MaintenanceQueryDTO {
    private Integer pageNum = 1;
    private Integer pageSize = 10;
    private Long roomId;
    private String status;
    private String maintenanceType;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
