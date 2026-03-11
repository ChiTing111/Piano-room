package com.bookingsystem.service;

import com.bookingsystem.dto.MaintenanceQueryDTO;
import com.bookingsystem.pojo.PageResult;
import com.bookingsystem.pojo.RoomMaintenance;

import java.util.List;

// MaintenanceService.java
public interface MaintenanceService {
    PageResult<RoomMaintenance> listMaintenance(MaintenanceQueryDTO dto);
    void addMaintenance(RoomMaintenance maintenance);
    void updateStatus(Long id, String status);

    void deleteBatch(List<Long> ids);

}