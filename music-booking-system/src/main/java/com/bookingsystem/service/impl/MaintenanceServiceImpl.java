package com.bookingsystem.service.impl;

import com.bookingsystem.dto.MaintenanceQueryDTO;
import com.bookingsystem.mapper.RoomMaintenanceMapper;
import com.bookingsystem.pojo.PageResult;
import com.bookingsystem.pojo.RoomMaintenance;
import com.bookingsystem.service.MaintenanceService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

// MaintenanceServiceImpl.java
@Service
public class MaintenanceServiceImpl implements MaintenanceService {

    @Autowired
    private RoomMaintenanceMapper mapper;

    @Override
    public PageResult<RoomMaintenance> listMaintenance(MaintenanceQueryDTO dto) {
        PageHelper.startPage(dto.getPageNum(), dto.getPageSize());
        List<RoomMaintenance> list = mapper.list(dto);
        PageInfo<RoomMaintenance> pageInfo = new PageInfo<>(list);
        return new PageResult<>(pageInfo.getTotal(), pageInfo.getList());
    }

    @Override
    public void addMaintenance(RoomMaintenance maintenance) {
        maintenance.setStatus("未开始");
        mapper.insert(maintenance);
    }

    @Override
    public void updateStatus(Long id, String status) {
        mapper.updateStatus(id, status);
    }

    @Override
    public void deleteBatch(List<Long> ids) {
        if (ids != null && !ids.isEmpty()) {
            mapper.deleteBatch(ids);
        }
    }


}