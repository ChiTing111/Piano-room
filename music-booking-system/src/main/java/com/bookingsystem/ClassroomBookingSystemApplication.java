package com.bookingsystem;

/**
 * 皮埃诺预约系统 · Piano Room Booking System
 * 作者：Jesse（刘家鑫）
 * 联系方式：13709406630
 */
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling //开启定时任务
@SpringBootApplication
@ServletComponentScan
public class ClassroomBookingSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClassroomBookingSystemApplication.class, args);
    }

}
