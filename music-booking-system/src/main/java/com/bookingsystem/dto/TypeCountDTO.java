package com.bookingsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class TypeCountDTO {
    private String typeName;  // 教室类型名称
    private Integer count;    // 对应数量
}
