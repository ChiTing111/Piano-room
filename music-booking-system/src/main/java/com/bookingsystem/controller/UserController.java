package com.bookingsystem.controller;

import com.bookingsystem.constants.Constants;
import com.bookingsystem.dto.*;
import com.bookingsystem.pojo.PageResult;
import com.bookingsystem.pojo.Result;
import com.bookingsystem.pojo.User;
import com.bookingsystem.service.ReservationService;
import com.bookingsystem.service.UserService;
import com.bookingsystem.utils.CaptchaUtil;
import com.bookingsystem.utils.SessionUtil;
import com.bookingsystem.vo.CaptchaResult;
import com.bookingsystem.vo.UserInfoVO;
import com.bookingsystem.vo.UserQueryVo;
import com.bookingsystem.vo.UserReservationStatsVO;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private ReservationService reservationService;



    @GetMapping("/getUserInfo")
    public Result getUserInfo(Long userId){
        User userInfoVO = userService.getById(userId);
        userInfoVO.setPassword("");
        return Result.success(userInfoVO);
    }
    /**
     *
     * @param request
     * @param type 0登录验证码  1注册验证码
     * @return
     * @throws IOException
     */
    @GetMapping("/captcha")
    public Result getCaptcha(HttpServletRequest request,@RequestParam("type") Integer type) throws IOException {
        CaptchaResult captchaResult = CaptchaUtil.generateCaptcha();
        switch (type){
            case 0:  SessionUtil.setAttribute(request, Constants.LOGIN_IMG_CODE, captchaResult.getCaptchaText());
            break;
            case 1:  SessionUtil.setAttribute(request, Constants.REGISTER_IMG_CODE, captchaResult.getCaptchaText());
            break;
        }
        return Result.success(captchaResult.getCaptchaImage());
    }

    /**
     * 用户登录
     * @param userLoginDTO
     * @return
     */
    @PostMapping("/login")
    public Result login(@RequestBody UserLoginDTO userLoginDTO, HttpServletRequest request){
        log.info("用户登录:{}",userLoginDTO);
        Map res = userService.login(userLoginDTO,request);
        return Result.success(res);
    }

    @PostMapping("/register")
    public Result register(@RequestBody RegisterInfoDTO registerInfoDTO,HttpServletRequest request){
        log.info("用户注册:{}",registerInfoDTO);
        userService.register(registerInfoDTO,request);
        return Result.success();
    }

    /**
     * 发送邮件验证码
     * @param email
     * @param type 验证码类型 0 注册 1 找回密码 2 修改密码
     * @return
     */
    @GetMapping("/email/code")
    public Result sendEmail(String email,Integer type,HttpServletRequest request) throws IOException {
        log.info("发送邮件:{},{}",email);
        userService.sendEmail(email,type,request);
        return Result.success();
    }


    /**
     * 获取用户列表(含条件查询)
     */
    @GetMapping("/list")
    public Result list(UserQueryDTO userQueryDTO){
        log.info("获取用户列表(含条件查询):{}",userQueryDTO);
        PageResult<UserQueryVo> pageResult = userService.list(userQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * 根据id获取用户信息
     */
    @GetMapping("/{id}")
    public Result getById(@PathVariable Long id){
        log.info("根据id获取用户信息:{}",id);
        User user = userService.getById(id);
        return Result.success(user);
    }
    /**
     * 重置用户密码
     */
    @PutMapping("/password")
    public Result resetPassword(@RequestBody ResetPasswordDTO resetPasswordDTO){
        log.info("重置用户密码:{}",resetPasswordDTO);
        try {
            userService.resetPassword(resetPasswordDTO);
            return Result.success();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error(e.getMessage());
        }
    }

    /**
     * 批量删除用户
     */
    @DeleteMapping
    public Result delete(Long[] ids){
        log.info("批量删除用户:{}",ids);
        userService.delete(ids);
        return Result.success();
    }
    /**
     * 启用禁用用户账号
     */
    @PostMapping("/status")
    public Result setStatus(@RequestBody UserStatusDTO userStatusDTO){
        log.info("启用禁用用户账号:{},{}",userStatusDTO.getStatus(),userStatusDTO.getUserId());
        Integer status = userStatusDTO.getStatus();
        Long userId = userStatusDTO.getUserId();
        userService.setStatus(status,userId);
        return Result.success();
    }

    /**
     * 更新用户信息
     */
    @PutMapping("/info")
    public Result update(@RequestBody User user){
        log.info("更新用户信息:{}",user);
        userService.update(user);
        return Result.success();
    }

    /**
     * 获取用户预约统计（个人中心使用）
     */
    @GetMapping("/{id}/reservation-stats")
    public Result getReservationStats(@PathVariable Long id){
        log.info("获取用户预约统计:{}", id);
        UserReservationStatsVO stats = reservationService.getUserReservationStats(id);
        return Result.success(stats);
    }

    /**
     * 添加用户（管理员用）
     */
    @PostMapping("/add")
    public Result addUser(@RequestBody User user){
        log.info("添加用户:{}",user);
        try {
            userService.addUser(user);
            return Result.success();
        } catch (Exception e) {
            log.error("添加用户失败", e);
            return Result.error("添加用户失败: " + e.getMessage());
        }
    }
}
