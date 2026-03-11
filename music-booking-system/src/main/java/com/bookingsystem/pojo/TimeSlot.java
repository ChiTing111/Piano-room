package com.bookingsystem.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

// TimeSlot.java
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TimeSlot {
    /**
     * 时间槽开始时间（格式：yyyy-MM-dd HH:mm）
     * 示例："2023-10-01 09:00"
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    /**
     * 时间槽结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

    /**
     * 是否可用
     */
    private boolean available;

    /**
     * 不可用时的冲突原因（前端展示用）
     */
    private String conflictReason;

    public TimeSlot(LocalDateTime startTime, LocalDateTime endTime) {
        this.startTime = Date.from(startTime.atZone(ZoneId.systemDefault()).toInstant());
        this.endTime = Date.from(endTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    // 辅助方法：转换为前端需要的ISO格式
    public String getIsoStart() {
        return Instant.ofEpochMilli(startTime.getTime())
                     .atZone(ZoneId.systemDefault())
                     .format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }

    public String getIsoEnd() {
        return Instant.ofEpochMilli(endTime.getTime())
                     .atZone(ZoneId.systemDefault())
                     .format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }
}