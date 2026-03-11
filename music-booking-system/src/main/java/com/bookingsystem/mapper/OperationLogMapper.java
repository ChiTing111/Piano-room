package com.bookingsystem.mapper;

import com.bookingsystem.dto.LogQueryDTO;
import com.bookingsystem.pojo.OperationLog;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OperationLogMapper {
    int insert(OperationLog operationLog);

    List<OperationLog> list(LogQueryDTO logQueryDTO);
}
