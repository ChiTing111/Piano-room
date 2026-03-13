package com.bookingsystem.controller;

import com.alibaba.fastjson2.JSON;
import com.bookingsystem.annotation.Log;
import com.bookingsystem.config.InMemoryDataStore;
import com.bookingsystem.pojo.BasicSetting;
import com.bookingsystem.pojo.ReservationSetting;
import com.bookingsystem.pojo.Result;
import com.bookingsystem.pojo.SecuritySetting;
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
        String baseSetting = inMemoryDataStore.get("baseSetting");
        if (baseSetting == null) {
            // 返回默认设置
            return Result.success(new BasicSetting());
        }
        return Result.success(JSON.parseObject(baseSetting, BasicSetting.class));
    }

    // 获取预约设置
    @GetMapping("/settings/reservation")
    public Result getReservationSettings() {
        String setting = inMemoryDataStore.get("reservationSetting");
        if (setting == null) {
            return Result.success(new ReservationSetting());
        }
        return Result.success(JSON.parseObject(setting, ReservationSetting.class));
    }

    // 获取安全设置
    @GetMapping("/settings/security")
    public Result getSecuritySettings() {
        String setting = inMemoryDataStore.get("securitySetting");
        if (setting == null) {
            return Result.success(new SecuritySetting());
        }
        return Result.success(JSON.parseObject(setting, SecuritySetting.class));
    }

    // 保存基本设置
    @Log(module = "系统设置", type = "更新", description = "更新基本设置")
    @PostMapping("/settings/basic")
    public Result saveBasicSettings(@RequestBody BasicSetting basicSetting) {
        // 将BasicSetting转换为JSON存储
        inMemoryDataStore.put("baseSetting", JSON.toJSONString(basicSetting));
        log.info("更新基本设置: {}", basicSetting);
        return Result.success();
    }

    // 保存预约设置
    @Log(module = "系统设置", type = "更新", description = "更新预约设置")
    @PostMapping("/settings/reservation")
    public Result saveReservationSettings(@RequestBody ReservationSetting reservationSetting) {
        inMemoryDataStore.put("reservationSetting", JSON.toJSONString(reservationSetting));
        log.info("更新预约设置: {}", reservationSetting);
        return Result.success();
    }

    // 保存安全设置
    @Log(module = "系统设置", type = "更新", description = "更新安全设置")
    @PostMapping("/settings/security")
    public Result saveSecuritySettings(@RequestBody SecuritySetting securitySetting) {
        inMemoryDataStore.put("securitySetting", JSON.toJSONString(securitySetting));
        log.info("更新安全设置: {}", securitySetting);
        return Result.success();
    }

    // 更新系统配置（兼容旧接口）
    @PutMapping
    public Result updateSystemConfig(@RequestBody SystemConfig systemConfig) {
        systemConfigService.updateSystemConfig(systemConfig);
        return Result.success();
    }
}