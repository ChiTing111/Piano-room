package com.bookingsystem.service.impl;

import com.bookingsystem.dto.CountReservationsDTO;
import com.bookingsystem.dto.RoomTypeUsageDTO;
import com.bookingsystem.dto.RoomUsageSummaryDTO;
import com.bookingsystem.mapper.ClassTypeMapper;
import com.bookingsystem.mapper.ReservationMapper;
import com.bookingsystem.mapper.RoomMapper;
import com.bookingsystem.mapper.UserMapper;
import com.bookingsystem.dto.TypeCountDTO;
import com.bookingsystem.pojo.TimeSlotReport;
import com.bookingsystem.service.ReportService;
import com.bookingsystem.vo.DayOfWeekCountVO;
import com.bookingsystem.vo.ReservationCountVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ReportServiceImpl implements ReportService {
    @Autowired
    private ReservationMapper reservationMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoomMapper roomMapper;

    @Autowired
    private ClassTypeMapper classTypeMapper;

    // 定义所有可能的时间段
    private static final List<String> TIME_SLOTS = Arrays.asList(
            "08:00 - 10:00", "10:00 - 12:00", "12:00 - 14:00",
            "14:00 - 16:00", "16:00 - 18:00", "18:00 - 20:00", "20:00 - 22:00"
    );

    @Override
    public Integer countReservations(CountReservationsDTO countReservationsDTO) {
        LocalDateTime[] times = calculateTimeRange(countReservationsDTO.getRange(), countReservationsDTO.getStart(), countReservationsDTO.getEnd());
        log.info("计算时间范围:{},{}",times[0],times[1]);
        countReservationsDTO.setStartTime(times[0]);
        countReservationsDTO.setEndTime(times[1]);
        int count = reservationMapper.countReserve(countReservationsDTO);
        return count;
    }

    @Override
    public double approvalRate(CountReservationsDTO countReservationsDTO) {
        LocalDateTime[] times = calculateTimeRange(countReservationsDTO.getRange(), countReservationsDTO.getStart(), countReservationsDTO.getEnd());
        log.info("计算时间范围:{},{}",times[0],times[1]);
        countReservationsDTO.setStartTime(times[0]);
        countReservationsDTO.setEndTime(times[1]);
        int num = reservationMapper.countReserve(countReservationsDTO); // 预约总数
        if (num > 0){
            countReservationsDTO.setStatus("approved");
            int count = reservationMapper.countReserve(countReservationsDTO);
            return (double) count / num;
        }
        return 0;
    }

    @Override
    public int activeUsers(CountReservationsDTO countReservationsDTO) {
        LocalDateTime[] times = calculateTimeRange(countReservationsDTO.getRange(), countReservationsDTO.getStart(), countReservationsDTO.getEnd());
        log.info("计算时间范围:{},{}",times[0],times[1]);
        countReservationsDTO.setStartTime(times[0]);
        countReservationsDTO.setEndTime(times[1]);
        int num = reservationMapper.countUser(countReservationsDTO);
        return num;
    }

    @Override
    public int registeredUsers() {
        return userMapper.getUserNum();
    }

    @Override
    public int pendingReservations() {
        CountReservationsDTO countReservationsDTO = new CountReservationsDTO();
        countReservationsDTO.setStatus("pending");
        return reservationMapper.countReserve(countReservationsDTO);
    }

    @Override
    public double classroomUsageRate(CountReservationsDTO countReservationsDTO) {
        LocalDateTime[] times = calculateTimeRange(countReservationsDTO.getRange(), countReservationsDTO.getStart(), countReservationsDTO.getEnd());
        log.info("计算时间范围:{},{}",times[0],times[1]);
        countReservationsDTO.setStartTime(times[0]);
        countReservationsDTO.setEndTime(times[1]);
        int sum = roomMapper.getAllRoom();
        log.info("教室总数sum:"+sum);
        if (sum > 0){
            int count = reservationMapper.countRoom(countReservationsDTO);
            return (double) count / sum;
        }
        return 0;
    }

    @Override
    public List<TypeCountDTO> getTypeStatistics() {
        return classTypeMapper.countRoomsByType();
    }

    @Override
    public List<ReservationCountVO> getReservationStatusDistribution() {
        return reservationMapper.getReservationStatusDistribution();
    }

    @Override
    public List<DayOfWeekCountVO> getWeeklyStatistics() {
        List<DayOfWeekCountVO> rawData = reservationMapper.countReservationsByDayOfWeek();
        return fillMissingDays(rawData);
    }

    @Override
    public List<TimeSlotReport> getReservationCounts(LocalDate startDate, LocalDate endDate) {
        // 获取当天的开始和结束时间
        LocalDateTime startDateTime = startDate.atStartOfDay(); // 00:00:00
        LocalDateTime endDateTime = endDate.atTime(LocalTime.MAX); // 23:59:59.999999

        // 从数据库获取有预约的时段数据
        List<TimeSlotReport> reports = reservationMapper.getReservationCountsByTimeSlot(startDateTime, endDateTime);

        // 创建一个映射，用于快速查找查询结果
        Map<String, Integer> reportMap = reports.stream()
                .collect(Collectors.toMap(TimeSlotReport::getTimeSlot, TimeSlotReport::getReservationCount));

        // 为每个时间段检查是否有数据，如果没有，则将其设置为0
        List<TimeSlotReport> result = new ArrayList<>();
        for (String timeSlot : TIME_SLOTS) {
            int count = reportMap.getOrDefault(timeSlot, 0); // 如果没有找到该时段，返回0
            TimeSlotReport report = new TimeSlotReport();
            report.setTimeSlot(timeSlot);
            report.setReservationCount(count);
            result.add(report);
        }

        return result;
    }

    @Override
    public RoomUsageSummaryDTO getRoomUsageSummary(LocalDateTime start, LocalDateTime end) {
        RoomUsageSummaryDTO dto = roomMapper.getRoomUsageSummary(start, end);
        int total = dto.getOccupiedCount() + dto.getFreeCount();
        if (total > 0) {
            double rate = dto.getOccupiedCount() * 100.0 / total;
            DecimalFormat df = new DecimalFormat("#.##");
            dto.setOccupiedRate(Double.parseDouble(df.format(rate)));
            rate = dto.getFreeCount() * 100.0 / total;
            dto.setFreeRate(Double.parseDouble(df.format(rate)));
        } else {
            dto.setOccupiedRate(0.0);
            dto.setFreeRate(0.0);
        }
        return dto;
    }

    @Override
    public List<RoomTypeUsageDTO> getUsageStats(LocalDateTime start, LocalDateTime end) {
        List<RoomTypeUsageDTO> list = roomMapper.getRoomTypeUsageStats(start, end);
        for (RoomTypeUsageDTO dto : list) {
            if (dto.getTotalRooms() != null && dto.getTotalRooms() > 0) {
                dto.setOccupiedRate(Math.round(dto.getOccupiedCount() * 100.0 / dto.getTotalRooms() * 100.0) / 100.0);
                dto.setFreeRate(Math.round(dto.getFreeCount() * 100.0 / dto.getTotalRooms() * 100.0) / 100.0);
            } else {
                dto.setOccupiedRate(0.0);
                dto.setFreeRate(0.0);
            }
        }
        return list;
    }

    // 补全没有数据的星期
    private List<DayOfWeekCountVO> fillMissingDays(List<DayOfWeekCountVO> rawData) {
        Map<String, Integer> dayMap = new LinkedHashMap<>() {{
            put("Sunday", 0);
            put("Monday", 0);
            put("Tuesday", 0);
            put("Wednesday", 0);
            put("Thursday", 0);
            put("Friday", 0);
            put("Saturday", 0);
        }};
        rawData.forEach(dto -> dayMap.put(dto.getDayOfWeek(), dto.getCount()));
        return dayMap.entrySet().stream()
                .map(entry -> new DayOfWeekCountVO(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }

    // 计算时间范围
    private LocalDateTime[] calculateTimeRange(String range, LocalDate start, LocalDate end) {
        if (start != null && end != null){
            return new LocalDateTime[]{
                    start.atStartOfDay(),
                    end.atTime(LocalTime.MAX)
            };
        }
        LocalDate now = LocalDate.now();
        switch (range) {
            case "TODAY":
                return new LocalDateTime[]{
                        now.atStartOfDay(),
                        now.atTime(LocalTime.MAX)
                };
            case "WEEK":
                LocalDate monday = now.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
                LocalDate sunday = now.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
                return new LocalDateTime[]{
                        monday.atStartOfDay(),
                        sunday.atTime(LocalTime.MAX)
                };
            case "MONTH":
                return new LocalDateTime[]{
                        now.withDayOfMonth(1).atStartOfDay(),
                        now.with(TemporalAdjusters.lastDayOfMonth()).atTime(LocalTime.MAX)
                };
            default:
                throw new IllegalArgumentException("无效的时间范围");
        }
    }


}
