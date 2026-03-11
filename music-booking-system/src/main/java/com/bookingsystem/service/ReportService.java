package com.bookingsystem.service;

import com.bookingsystem.dto.CountReservationsDTO;
import com.bookingsystem.dto.RoomTypeUsageDTO;
import com.bookingsystem.dto.RoomUsageSummaryDTO;
import com.bookingsystem.dto.TypeCountDTO;
import com.bookingsystem.pojo.TimeSlotReport;
import com.bookingsystem.vo.DayOfWeekCountVO;
import com.bookingsystem.vo.ReservationCountVO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface ReportService {
    Integer countReservations(CountReservationsDTO countReservationsDTO);

    double approvalRate(CountReservationsDTO countReservationsDTO);

    int activeUsers(CountReservationsDTO countReservationsDTO);

    int registeredUsers();

    int pendingReservations();

    double classroomUsageRate(CountReservationsDTO countReservationsDTO);

    List<TypeCountDTO> getTypeStatistics();

    List<ReservationCountVO> getReservationStatusDistribution();

    List<DayOfWeekCountVO> getWeeklyStatistics();

    List<TimeSlotReport> getReservationCounts(LocalDate startDate, LocalDate endDate);

    RoomUsageSummaryDTO getRoomUsageSummary(LocalDateTime start, LocalDateTime end);

    List<RoomTypeUsageDTO> getUsageStats(LocalDateTime start, LocalDateTime end);

}
