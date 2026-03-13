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

    @Scheduled(cron = "0 * * * * ?") //每分钟执行一次
    public void checkRoomStatus() {
        log.info("开始检查教室状态");
        //查询系统维护表信息
        List<RoomMaintenance> list = roomMapper.getAllMaintenance();
        LocalDateTime now = LocalDateTime.now();
        log.info("当前时间: {}, 查询到 {} 条维修记录", now, list.size());

        //遍历list集合
        for (RoomMaintenance roomMaintenance : list) {
            log.info("检查维修记录: id={}, roomId={}, startTime={}, endTime={}, status={}",
                    roomMaintenance.getId(), roomMaintenance.getRoomId(),
                    roomMaintenance.getStartTime(), roomMaintenance.getEndTime(),
                    roomMaintenance.getStatus());

            if ("已取消".equals(roomMaintenance.getStatus())){
                log.info("维修记录 {} 已取消，跳过", roomMaintenance.getId());
                continue;
            }

            // 维修期间：琴房停用（status=0）
            if (now.isAfter(roomMaintenance.getStartTime()) && now.isBefore(roomMaintenance.getEndTime())){
                roomMapper.setStatus(0, roomMaintenance.getRoomId());
                // 同时更新维修记录状态为"进行中"
                mapper.updateStatus(roomMaintenance.getId(), "进行中");
                log.info("琴房 {} 进入维修状态，已停用", roomMaintenance.getRoomId());
            }
            // 维修结束：琴房启用（status=1）
            else if (now.isAfter(roomMaintenance.getEndTime())){
                roomMapper.setStatus(1, roomMaintenance.getRoomId());
                // 同时更新维修记录状态为"已完成"
                mapper.updateStatus(roomMaintenance.getId(), "已完成");
                log.info("琴房 {} 维修结束，已启用", roomMaintenance.getRoomId());
            }
            // 维修未开始
            else if (now.isBefore(roomMaintenance.getStartTime())){
                mapper.updateStatus(roomMaintenance.getId(), "未开始");
                log.info("维修记录 {} 状态: 未开始", roomMaintenance.getId());
            }
        }
    }
}
