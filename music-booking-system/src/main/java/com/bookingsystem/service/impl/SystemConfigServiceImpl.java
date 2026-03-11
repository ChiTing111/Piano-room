package com.bookingsystem.service.impl;

import com.bookingsystem.config.InMemoryDataStore;
import com.bookingsystem.mapper.SystemConfigMapper;
import com.bookingsystem.pojo.SystemConfig;
import com.bookingsystem.service.SystemConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class SystemConfigServiceImpl implements SystemConfigService {

    @Autowired
    private SystemConfigMapper systemConfigMapper;

    @Autowired
    private InMemoryDataStore inMemoryDataStore;

    @Override
    public SystemConfig getSystemConfig() {
        return systemConfigMapper.getSystemConfig();
    }

    @Override
    @Transactional
    public void updateSystemConfig(SystemConfig systemConfig) {
        systemConfig.setUpdatedAt(LocalDateTime.now());
        systemConfigMapper.updateSystemConfig(systemConfig);
        inMemoryDataStore.put(systemConfig.getSystemName(), systemConfig.getDescription());

    }
}