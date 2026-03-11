package com.bookingsystem.utils;

import com.bookingsystem.vo.CaptchaResult;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Random;

public class CaptchaUtil {

    private static final int WIDTH = 100; // 图像宽度
    private static final int HEIGHT = 40; // 图像高度
    private static final int CODE_LENGTH = 4; // 验证码字符长度
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"; // 验证码字符集

    public static CaptchaResult generateCaptcha() throws IOException {
        // 生成随机验证码
        StringBuilder captcha = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < CODE_LENGTH; i++) {
            int index = random.nextInt(CHARACTERS.length());
            captcha.append(CHARACTERS.charAt(index));
        }

        // 创建图像
        BufferedImage bufferedImage = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = bufferedImage.createGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, WIDTH, HEIGHT);
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 30));
        g.drawString(captcha.toString(), 10, 30);

        // 将图像转换为 Base64 编码
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "png", baos);
        byte[] imageBytes = baos.toByteArray();
        String base64Image = "data:image/png;base64," + java.util.Base64.getEncoder().encodeToString(imageBytes);

        // 创建并返回 CaptchaResult 对象
        CaptchaResult result = new CaptchaResult();
        result.setCaptchaText(captcha.toString());
        result.setCaptchaImage(base64Image);
        return result; // 返回验证码文本和图像的 Base64 编码
    }
}