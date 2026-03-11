package com.bookingsystem.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationSetting {
    private Long id;
    private Long userId;
    private Integer emailReservation;
    private Integer emailReminder;
    private Integer emailAnnouncement;
    private Integer smsReservation;
    private Integer smsReminder;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}