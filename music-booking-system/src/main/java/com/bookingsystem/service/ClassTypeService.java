package com.bookingsystem.service;

import com.bookingsystem.pojo.ClassType;

import java.util.List;
import java.util.Map;

public interface ClassTypeService {
    boolean addClassType(ClassType classType);
    boolean deleteClassType(Long id);
    boolean updateClassType(Long id, ClassType classType);
    Map getAllClassTypes(Integer pageNum, Integer pageSize, String typeName);
    ClassType getClassTypeById(Long id);


    List<ClassType> list();
}