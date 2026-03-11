package com.bookingsystem.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OperationLog {
    private Long id;
    private String username;
    private String operationModule;
    private String operationType;
    private String operationDesc;
    private String requestUrl;
    private String requestMethod;
    private String requestIp;
    private String requestParam;
    private String responseResult;
    private Integer status;
    private String errorMsg;
    private LocalDateTime createdAt;
}
