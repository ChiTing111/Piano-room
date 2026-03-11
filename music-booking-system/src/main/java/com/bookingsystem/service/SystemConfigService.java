package com.bookingsystem.service;

import com.bookingsystem.pojo.SystemConfig;

public interface SystemConfigService {
    // 获取系统配置
    SystemConfig getSystemConfig();
    // 更新系统配置
    void updateSystemConfig(SystemConfig systemConfig);
}