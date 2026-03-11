package com.bookingsystem.service;


import com.bookingsystem.pojo.Reservation;

/**
 * 预约邮件通知服务
 */
public interface ReservationNotificationService {
    
    /**
     * 发送预约创建通知
     * 
     * @param reservation 预约信息
     * @param userEmail 用户邮箱
     * @param userName 用户姓名
     * @return 发送结果
     */
    boolean sendReservationCreatedNotification(Reservation reservation, String userEmail, String userName);
    
    /**
     * 发送预约状态变更通知
     * 
     * @param reservation 预约信息
     * @param userEmail 用户邮箱
     * @param userName 用户姓名
     * @return 发送结果
     */
    boolean sendReservationStatusChangedNotification(Reservation reservation, String userEmail, String userName);
    
    /**
     * 发送预约即将到来提醒
     * 
     * @param reservation 预约信息
     * @param userEmail 用户邮箱
     * @param userName 用户姓名
     * @return 发送结果
     */
    boolean sendReservationReminderNotification(Reservation reservation, String userEmail, String userName);
}