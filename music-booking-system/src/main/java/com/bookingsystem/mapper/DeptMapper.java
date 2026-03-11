package com.bookingsystem.mapper;

import com.bookingsystem.pojo.Department;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface DeptMapper {

    @Select("select id, name, code, created_at, updated_at from departments")
    List<Department> findAll();

    @Delete("delete from departments where id = #{id}")
    void deleteById(Integer id);

    @Insert("insert into departments (name, code, created_at, updated_at) values (#{name}, #{code}, #{createdAt}, #{updatedAt})")
    void addDept(Department dept);

    @Select("select id, name, code, created_at, updated_at from departments where id = #{id}")
    Department getById(Integer id);

    @Update("update departments set name = #{name}, code = #{code}, updated_at = #{updatedAt} where id = #{id}")
    void update(Department dept);
}
