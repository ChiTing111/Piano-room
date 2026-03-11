package com.bookingsystem.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

// ReservationRequestDTO.java
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationDTO {
    private Long roomId;
    private String title;
    private String purpose;

    private Long userId;


    @Future(message = "只能预约未来时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;


    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Future(message = "只能预约未来时间")
    private LocalDateTime endTime;

    private Integer attendees;

    private String remarks;
}