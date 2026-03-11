package com.bookingsystem.service.impl;


import com.bookingsystem.pojo.Reservation;
import com.bookingsystem.service.EmailService;
import com.bookingsystem.service.ReservationNotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/**
 * 预约邮件通知服务实现
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class ReservationNotificationServiceImpl implements ReservationNotificationService {
    
    private final EmailService emailService;
    
    @Value("${app.frontend.url:http://localhost:8080}")
    private String frontendUrl;
    
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    
    @Override
    public boolean sendReservationCreatedNotification(Reservation reservation, String userEmail, String userName) {
        Map<String, Object> variables = createCommonTemplateVariables(reservation, userName);
        variables.put("statusText", "已成功提交");
        variables.put("detailUrl", frontendUrl + "/reservations/detail/" + reservation.getId());
        
        String subject = "【教室预约系统】预约申请已提交 - " + reservation.getTitle();
        return emailService.sendTemplateMail(userEmail, subject, "reservation-notification", variables);
    }
    
    @Override
    public boolean sendReservationStatusChangedNotification(Reservation reservation, String userEmail, String userName) {
        String statusText;
        String subject;
        
        switch (reservation.getStatus()) {
            case "approved":
                statusText = "已审核通过";
                subject = "【教室预约系统】预约申请已通过 - " + reservation.getTitle();
                break;
            case "rejected":
                statusText = "已被拒绝";
                subject = "【教室预约系统】预约申请未通过 - " + reservation.getTitle();
                break;
            case "cancelled":
                statusText = "已取消";
                subject = "【教室预约系统】预约已取消 - " + reservation.getTitle();
                break;
            case "completed":
                statusText = "已完成";
                subject = "【教室预约系统】预约已完成 - " + reservation.getTitle();
                break;
            default:
                statusText = "状态已更新";
                subject = "【教室预约系统】预约状态更新 - " + reservation.getTitle();
        }
        
        Map<String, Object> variables = createCommonTemplateVariables(reservation, userName);
        variables.put("statusText", statusText);
        variables.put("detailUrl", frontendUrl + "/reservations/detail/" + reservation.getId());
        
        return emailService.sendTemplateMail(userEmail, subject, "reservation-notification", variables);
    }
    
    @Override
    public boolean sendReservationReminderNotification(Reservation reservation, String userEmail, String userName) {
        Map<String, Object> variables = createCommonTemplateVariables(reservation, userName);
        variables.put("statusText", "即将开始");
        variables.put("detailUrl", frontendUrl + "/reservations/detail/" + reservation.getId());
        
        String subject = "【教室预约系统】预约即将开始提醒 - " + reservation.getTitle();
        return emailService.sendTemplateMail(userEmail, subject, "reservation-reminder", variables);
    }
    
    /**
     * 创建通用模板变量
     */
    private Map<String, Object> createCommonTemplateVariables(Reservation reservation, String userName) {
        Map<String, Object> variables = new HashMap<>();
        
        variables.put("userName", userName);
        variables.put("reservationId", reservation.getId());
        variables.put("roomName", getRoomName(reservation));
        variables.put("reservationTime", formatReservationTime(reservation));
        variables.put("purpose", formatPurpose(reservation.getPurpose()));
        variables.put("status", reservation.getStatus());
        variables.put("remarks", reservation.getRemarks());
        variables.put("reviewRemarks", reservation.getReviewRemarks());
        
        return variables;
    }
    
    /**
     * 获取教室名称（实际应用中需要通过roomId查询）
     */
    private String getRoomName(Reservation reservation) {
        // 实际应用中应该通过roomId查询教室信息
        return "教室 #" + reservation.getRoomId();
    }
    
    /**
     * 格式化预约时间
     */
    private String formatReservationTime(Reservation reservation) {
        String startTime = reservation.getStartTime().format(DATE_TIME_FORMATTER);
        String endTime = reservation.getEndTime().format(DATE_TIME_FORMATTER);
        return startTime + " - " + endTime;
    }
    
    /**
     * 格式化预约用途
     */
    private String formatPurpose(String purpose) {
        switch (purpose) {
            case "teaching": return "教学活动";
            case "student": return "学生活动";
            case "meeting": return "会议";
            case "training": return "培训";
            case "other": return "其他";
            default: return purpose;
        }
    }
}