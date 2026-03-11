package com.bookingsystem.pojo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SystemConfig {
    private Long id;
    private String systemName;
    private String logoUrl;
    private String description;
    private String adminEmail;
    private String contactPhone;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
