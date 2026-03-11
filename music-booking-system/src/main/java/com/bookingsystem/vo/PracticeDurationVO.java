package com.bookingsystem.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.Duration;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PracticeDurationVO {
    private Long id;                    // 预约ID
    private Long userId;                 // 用户ID
    private String realName;             // 预约人姓名
    private String phone;                // 手机号
    private String grade;                // 年级
    private String major;                // 专业
    private Long roomId;                 // 琴房ID
    private String roomNumber;           // 琴房号
    private String roomName;             // 琴房名称
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;     // 预约开始时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;      // 预约结束时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime signStartTime; // 签到时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime signEndTime;  // 签退时间
    private Long practiceMinutes;       // 练习时长（分钟）
    private String practiceDuration;     // 练习时长（格式化字符串，如：1小时30分钟）
    
    /**
     * 计算练习时长
     */
    public void calculatePracticeDuration() {
        if (signStartTime != null && signEndTime != null) {
            Duration duration = Duration.between(signStartTime, signEndTime);
            practiceMinutes = duration.toMinutes();
            
            long hours = duration.toHours();
            long minutes = duration.toMinutes() % 60;
            
            if (hours > 0) {
                practiceDuration = hours + "小时" + (minutes > 0 ? minutes + "分钟" : "");
            } else {
                practiceDuration = minutes + "分钟";
            }
        } else {
            practiceMinutes = null;
            practiceDuration = "未完成";
        }
    }
}
