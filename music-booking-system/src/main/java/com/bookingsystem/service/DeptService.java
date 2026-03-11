package com.bookingsystem.service;

import com.bookingsystem.pojo.Department;

import java.util.List;

public interface DeptService {
    List<Department> findAll();

    void deleteById(Integer id);

    void addDept(Department dept);

    Department getById(Integer id);

    void update(Department dept);
}
