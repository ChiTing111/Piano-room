package com.bookingsystem.mapper;

import com.bookingsystem.pojo.ClassType;
import com.bookingsystem.dto.TypeCountDTO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ClassTypeMapper {

    @Insert("INSERT INTO room_types(type_name, description, created_at, updated_at) VALUES(#{typeName}, #{description}, #{createdAt}, #{updatedAt})")
    int insert(ClassType classType);

    @Delete("DELETE FROM room_types WHERE id = #{id}")
    int deleteById(Long id);

    @Update("UPDATE room_types SET type_name = #{typeName}, description = #{description}, updated_at = #{updatedAt} WHERE id = #{id}")
    int updateById(ClassType classType);

    List<ClassType> selectAll(@Param("typeName") String typeName);

    @Select("SELECT * FROM room_types WHERE id = #{id}")
    ClassType selectById(Long id);

    @Select("SELECT COUNT(*) FROM rooms WHERE room_type_id = #{id}")
    Integer selectAvailableClassrooms(Long id);

    @Select("SELECT type_name FROM room_types")
    List<String> selectAllName();

    @Select("SELECT rt.type_name AS typeName, COUNT(r.id) AS count " +
            "FROM room_types rt " +
            "LEFT JOIN rooms r ON rt.id = r.room_type_id " +
            "GROUP BY rt.id")
    List<TypeCountDTO> countRoomsByType();
}