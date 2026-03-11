package com.bookingsystem.service;

import com.bookingsystem.dto.UserLoginDTO;
import com.bookingsystem.dto.UserQueryDTO;
import com.bookingsystem.pojo.PageResult;
import com.bookingsystem.pojo.User;
import com.bookingsystem.vo.UserQueryVo;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Map;

public interface AdminService {
    PageResult<UserQueryVo> list(UserQueryDTO userQueryDTO);

    User getById(Long id);

    void resetPassword(Long id,String password);

    void delete(Long[] ids);

    void setStatus(Integer status, Long id);


    void update(User user);

    void save(User user);
}