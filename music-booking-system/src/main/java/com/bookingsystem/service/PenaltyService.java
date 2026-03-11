package com.bookingsystem.service;

import com.bookingsystem.pojo.PenaltyRule;

import java.util.List;

public interface PenaltyService {

    List<PenaltyRule> listRules();

    void updateRule(PenaltyRule rule);

    /**
     * 对用户增加一次违约记录，并根据规则判断是否封禁
     */
    void recordViolation(Long userId);

    /**
     * 解除用户封禁
     */
    void removeBan(Long userId);
}
