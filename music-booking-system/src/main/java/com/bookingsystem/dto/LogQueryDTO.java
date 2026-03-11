package com.bookingsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LogQueryDTO {
    private Integer page = 1;                  // 当前页码，默认第一页
    private Integer pageSize = 10;             // 每页数量，默认10条
    private String username;                   // 操作用户名（模糊匹配）
    private String operationModule;            // 操作模块
    private Integer status;
    private String operationType;              // 操作类型
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endTime;

}
