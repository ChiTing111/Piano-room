package com.bookingsystem.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Reservation {
    private Long id;
    private Long userId;
    private Long roomId;
    private String title;
    private String purpose;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;
    private Integer attendees;
    private String status;
    private String remarks;
    private Long reviewerId;
    private LocalDateTime reviewTime;
    private String reviewRemarks;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime signStartTime; // 签到时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime signEndTime;   // 签退时间

    /**
     * 拓展字段
     */
    private String roomName;


    /**
     * 拓展字段
     */
    private String username;

    /**
     * 拓展字段教室位置
     */
    private String roomLocation;

    /**
     * 拓展字段联系方式
     */
    private String phone;

}