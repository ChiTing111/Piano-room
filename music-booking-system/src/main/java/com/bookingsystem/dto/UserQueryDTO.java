package com.bookingsystem.dto;

import lombok.Data;

@Data
public class UserQueryDTO {
    private String userType; //用户角色
    private Integer status; //用户状态
    private String usernameOrRealNameOrStudentId; //用户名、真实姓名、学号
    private Integer page = 1; //默认第一页
    private Integer pageSize = 10; //默认每页10条数据
}
