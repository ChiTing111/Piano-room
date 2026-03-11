package com.bookingsystem.service.impl;

import com.bookingsystem.dto.UserQueryDTO;
import com.bookingsystem.mapper.UserMapper;
import com.bookingsystem.pojo.PageResult;
import com.bookingsystem.pojo.User;
import com.bookingsystem.service.AdminService;
import com.bookingsystem.utils.Md5Util;
import com.bookingsystem.vo.UserQueryVo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public PageResult<UserQueryVo> list(UserQueryDTO userQueryDTO) {
        PageHelper.startPage(userQueryDTO.getPage(), userQueryDTO.getPageSize());
        List<UserQueryVo> list = userMapper.selectAdmins(userQueryDTO);
        Page<UserQueryVo> page = (Page<UserQueryVo>) list;
        return new PageResult<>(page.getTotal(), page.getResult());
    }

    @Override
    public User getById(Long id) {
        return userMapper.getById(id);
    }

    @Override
    public void resetPassword(Long id, String password) {
        String newPassword = Md5Util.getMD5String(password);
        userMapper.resetPassword(id, newPassword);
    }

    @Override
    public void delete(Long[] ids) {
        userMapper.delete(ids);
    }

    @Override
    public void setStatus(Integer status, Long id) {
        userMapper.setStatus(status, id);
    }

    @Override
    public void update(User user) {
        user.setUpdatedAt(LocalDateTime.now());
        userMapper.update(user);
    }

    @Override
    public void save(User user) {
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        user.setPassword(Md5Util.getMD5String(user.getPassword()));
        log.info("user:{}", user);
        userMapper.insert(user);
    }
}