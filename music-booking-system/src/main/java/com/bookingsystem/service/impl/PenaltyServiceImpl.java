package com.bookingsystem.service.impl;

import com.bookingsystem.mapper.PenaltyRuleMapper;
import com.bookingsystem.mapper.UserMapper;
import com.bookingsystem.pojo.PenaltyRule;
import com.bookingsystem.pojo.User;
import com.bookingsystem.service.PenaltyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class PenaltyServiceImpl implements PenaltyService {

    @Autowired
    private PenaltyRuleMapper penaltyRuleMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<PenaltyRule> listRules() {
        return penaltyRuleMapper.listAll();
    }

    @Override
    public void updateRule(PenaltyRule rule) {
        penaltyRuleMapper.update(rule);
    }

    @Override
    @Transactional
    public void recordViolation(Long userId) {
        // 增加违约次数
        userMapper.incrementViolationCount(userId);

        // 重新获取最新违约次数
        User user = userMapper.getViolationInfo(userId);
        int newCount = user.getViolationCount() != null ? user.getViolationCount() : 1;

        // 查询适用的惩罚规则
        PenaltyRule rule = penaltyRuleMapper.findApplicableRule(newCount);
        if (rule != null && rule.getBanDays() > 0) {
            LocalDateTime banUntil = LocalDateTime.now().plusDays(rule.getBanDays());
            userMapper.setBanUntil(userId, banUntil);
            log.info("用户 {} 违约 {} 次，封禁至 {}", userId, newCount, banUntil);
        } else {
            log.info("用户 {} 违约 {} 次，仅警告", userId, newCount);
        }
    }

    @Override
    public void removeBan(Long userId) {
        userMapper.removeBan(userId);
        log.info("管理员解除用户 {} 的封禁", userId);
    }
}
