package com.bookingsystem.service.impl;

import com.bookingsystem.mapper.ClassTypeMapper;
import com.bookingsystem.pojo.ClassType;
import com.bookingsystem.service.ClassTypeService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class ClassTypeServiceImpl implements ClassTypeService {

    @Autowired
    private ClassTypeMapper classTypeMapper;

    @Override
    public boolean addClassType(ClassType classType) {
        classType.setCreatedAt(LocalDateTime.now());
        classType.setUpdatedAt(LocalDateTime.now());
        log.info("添加教室类型:{}", classType);
        return classTypeMapper.insert(classType) > 0;
    }

    @Override
    public boolean deleteClassType(Long id) {
        //获取该教师类型下面有无关联教室
        if (classTypeMapper.selectAvailableClassrooms(id)> 0) {
            return false;
        }
        return classTypeMapper.deleteById(id) > 0;
    }

    @Override
    public boolean updateClassType(Long id, ClassType classType) {
        classType.setId(id);
        classType.setUpdatedAt(LocalDateTime.now());
        return classTypeMapper.updateById(classType) > 0;
    }

    @Override
    public Map getAllClassTypes(Integer pageNum, Integer pageSize, String typeName) {
        PageHelper.startPage(pageNum,pageSize);
        List<ClassType> classTypes = classTypeMapper.selectAll(typeName);
        Page<ClassType> page = (Page<ClassType>) classTypes;
        Map res = new HashMap();
        res.put("total",page.getTotal());
        res.put("list",page.getResult());
        return res;
    }

    @Override
    public ClassType getClassTypeById(Long id) {
        return classTypeMapper.selectById(id);
    }

    @Override
    public List<ClassType> list() {
        List<ClassType> classTypes = classTypeMapper.selectAll(null);
        return classTypes;
    }
}