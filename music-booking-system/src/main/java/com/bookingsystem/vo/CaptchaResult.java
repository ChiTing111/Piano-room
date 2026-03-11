package com.bookingsystem.vo;

import lombok.Data;

@Data
public class CaptchaResult {
    private String captchaText; // 验证码文本
    private String captchaImage; // 验证码图像的 Base64 编码
}