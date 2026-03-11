package com.bookingsystem.controller;

import com.alibaba.fastjson2.JSON;
import com.bookingsystem.config.InMemoryDataStore;
import com.bookingsystem.pojo.BasicSetting;
import com.bookingsystem.pojo.Result;
import com.bookingsystem.pojo.SystemConfig;
import com.bookingsystem.service.SystemConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/system")
public class SystemConfigController {

    @Autowired
    private SystemConfigService systemConfigService;
    @Autowired
    private InMemoryDataStore inMemoryDataStore;

    // 获取系统基本配置
    @GetMapping("/settings/basic")
    public Result getSystemConfig() {
        String baseSetting =  inMemoryDataStore.get("baseSetting");

        return Result.success( JSON.parseObject(baseSetting, BasicSetting.class));
    }

    // 更新系统配置
    @PutMapping
    public Result updateSystemConfig(@RequestBody SystemConfig systemConfig) {
        systemConfigService.updateSystemConfig(systemConfig);
        return Result.success();
    }
}