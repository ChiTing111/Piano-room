package com.bookingsystem.service.impl;

import com.bookingsystem.mapper.DeptMapper;
import com.bookingsystem.pojo.Department;
import com.bookingsystem.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DeptServiceImpl implements DeptService {

    @Autowired
    private DeptMapper deptMapper;

    @Override
    public List<Department> findAll() {
        return deptMapper.findAll();
    }

    @Override
    public void deleteById(Integer id) {
        deptMapper.deleteById(id);
    }

    @Override
    public void addDept(Department dept) {
        dept.setCreatedAt(LocalDateTime.now());
        dept.setUpdatedAt(LocalDateTime.now());
        deptMapper.addDept(dept);
    }

    @Override
    public Department getById(Integer id) {
        return deptMapper.getById(id);
    }

    @Override
    public void update(Department dept) {
        dept.setUpdatedAt(LocalDateTime.now());
        deptMapper.update(dept);
    }
}
