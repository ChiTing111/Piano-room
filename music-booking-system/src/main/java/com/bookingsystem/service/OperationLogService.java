package com.bookingsystem.service;

import com.bookingsystem.dto.LogQueryDTO;
import com.bookingsystem.pojo.OperationLog;
import com.bookingsystem.pojo.PageResult;

public interface OperationLogService {
    void save(OperationLog operationLog);

    PageResult<OperationLog> page(LogQueryDTO logQueryDTO);
}
