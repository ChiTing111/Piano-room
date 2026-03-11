package com.bookingsystem.mapper;

import com.bookingsystem.pojo.PenaltyRule;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface PenaltyRuleMapper {

    @Select("SELECT * FROM penalty_rules ORDER BY violation_count ASC")
    List<PenaltyRule> listAll();

    @Select("SELECT * FROM penalty_rules WHERE id = #{id}")
    PenaltyRule getById(@Param("id") Long id);

    @Update("UPDATE penalty_rules SET ban_days = #{banDays}, description = #{description}, updated_at = NOW() WHERE id = #{id}")
    void update(PenaltyRule rule);

    /**
     * 查询违约次数对应的最高惩罚规则（小于等于当前次数的最大阈值规则）
     */
    @Select("SELECT * FROM penalty_rules WHERE violation_count <= #{count} ORDER BY violation_count DESC LIMIT 1")
    PenaltyRule findApplicableRule(@Param("count") Integer count);
}
