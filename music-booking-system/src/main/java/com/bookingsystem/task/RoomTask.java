package com.bookingsystem.task;

import com.bookingsystem.mapper.RoomMaintenanceMapper;
import com.bookingsystem.mapper.RoomMapper;
import com.bookingsystem.pojo.Room;
import com.bookingsystem.pojo.RoomMaintenance;
import com.bookingsystem.vo.RoomQueryVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Component
public class RoomTask {
    @Autowired
    private RoomMapper roomMapper;

    @Autowired
    private RoomMaintenanceMapper mapper;

    @Scheduled(cron = "0 0 * * * ?") //每小时执行一次
    public void checkRoomStatus() {
        log.info("开始检查教室状态");
        //查询系统维护表信息
        List<RoomMaintenance> list = roomMapper.getAllMaintenance();
        //遍历list集合
        for (RoomMaintenance roomMaintenance : list) {
            if (roomMaintenance.getStatus() == "已取消"){
                continue;
            }
            if (roomMaintenance.getStartTime().isBefore(LocalDateTime.now()) && roomMaintenance.getEndTime().isAfter(LocalDateTime.now())){
                roomMapper.setStatus(0,roomMaintenance.getRoomId());
            }
            if (roomMaintenance.getEndTime().isBefore(LocalDateTime.now())){
                roomMapper.setStatus(1,roomMaintenance.getRoomId());
            }
        }
    }

    @Scheduled(cron = "0 0 * * * ?") //每小时执行一次
    public void refreshStatuses() {
        List<RoomMaintenance> all = mapper.selectAll();
        LocalDateTime now = LocalDateTime.now();
        for (RoomMaintenance m : all) {
            if (m.getStatus() == "已取消"){
                continue;
            }
            String status;
            if (now.isBefore(m.getStartTime())) {
                status = "未开始";
            } else if (now.isAfter(m.getEndTime())) {
                status = "已完成";
            } else {
                status = "进行中";
            }
            mapper.updateStatus(m.getId(), status);
        }
    }
}
