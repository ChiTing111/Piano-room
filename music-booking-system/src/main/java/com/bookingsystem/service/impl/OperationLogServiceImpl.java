package com.bookingsystem.service.impl;

import com.bookingsystem.dto.LogQueryDTO;
import com.bookingsystem.mapper.OperationLogMapper;
import com.bookingsystem.pojo.OperationLog;
import com.bookingsystem.pojo.PageResult;
import com.bookingsystem.service.OperationLogService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OperationLogServiceImpl implements OperationLogService {

    @Autowired
    private OperationLogMapper operationLogMapper;

    @Override
    public void save(OperationLog operationLog) {
        operationLogMapper.insert(operationLog);
    }

    @Override
    public PageResult<OperationLog> page(LogQueryDTO logQueryDTO) {
        PageHelper.startPage(logQueryDTO.getPage(),  logQueryDTO.getPageSize());
        if (logQueryDTO.getEndTime() != null) {
            logQueryDTO.setEndTime(logQueryDTO.getEndTime().plusDays(1));
        }
        List<OperationLog> lists = operationLogMapper.list(logQueryDTO);
        Page<OperationLog> p = (Page<OperationLog>) lists;
        return new PageResult<>(p.getTotal(),p.getResult());
    }
}
