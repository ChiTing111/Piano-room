package com.bookingsystem.mapper;

import com.bookingsystem.dto.MaintenanceQueryDTO;
import com.bookingsystem.pojo.RoomMaintenance;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

// RoomMaintenanceMapper.java
@Mapper
public interface RoomMaintenanceMapper {
    List<RoomMaintenance> list(MaintenanceQueryDTO dto);

    void insert(RoomMaintenance maintenance);

    void updateStatus(@Param("id") Long id, @Param("status") String status);

    List<RoomMaintenance> selectAll();

    void deleteBatch(@Param("ids") List<Long> ids);

}