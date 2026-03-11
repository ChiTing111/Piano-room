package com.bookingsystem.service.impl;


import com.bookingsystem.mapper.ReservationMapper;
import com.bookingsystem.mapper.UserMapper;
import com.bookingsystem.pojo.Reservation;
import com.bookingsystem.service.ReservationNotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 预约提醒服务
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class ReservationReminderService {
    
    private final ReservationMapper reservationRepository;
    private final UserMapper userRepository;
    private final ReservationNotificationService notificationService;
    

}