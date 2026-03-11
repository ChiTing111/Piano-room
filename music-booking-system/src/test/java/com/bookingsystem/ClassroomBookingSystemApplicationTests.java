//package com.bookingsystem;
//
//import com.bookingsystem.mapper.UserMapper;
//import com.bookingsystem.pojo.User;
//import com.bookingsystem.service.EmailService;
//import com.bookingsystem.utils.Md5Util;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.time.Year;
//import java.util.HashMap;
//import java.util.Map;
//
//@SpringBootTest
//class ClassroomBookingSystemApplicationTests {
//
//    @Autowired
//    private EmailService emailService;
//
//    @Autowired
//    private UserMapper userMapper;
//
//    @Test
//    void contextLoads() {
//    }
//
//    @Test
//    public void testSendSimpleMail() {
//        emailService.sendSimpleMail("lapis17@qq.com", "Test Subject", "Test Content");
//    }
//
//
//    @Test
//    public void testSendSimpleMailTemplate() {
//
//
//        Map<String, Object> variables = new HashMap<>();
//        variables.put("code", "ashkas");
//        variables.put("validTime", "15分钟");
//        variables.put("userName", "用户");
//        variables.put("currentYear", Year.now().getValue());
//
//        String subject = "【教室预约系统】您的验证码";
//         emailService.sendTemplateMail("13471392@qq.com", subject, "verification-code", variables);
//
//
//    }
//
//    @Test
//    public void insertAdmin() {
//        User user = new User()
//                .setUsername("admin")
//                .setPassword(Md5Util.getMD5String("admin123"))
//                .setRealName("管理员")
//                .setUserType("super_admin")
//                .setStatus(1)
//                .setEmail("example@example.com")
////                .setDepartmentId(1L);
//        userMapper.insert(user);
//    }
//
//    @Test
//    public void insertStudent() {
//        User user = new User()
//                .setUsername("student01")
//                .setPassword(Md5Util.getMD5String("student123"))
//                .setRealName("学生1")
//                .setUserType("student")
//                .setStatus(1)
//                .setEmail("example@example.com")
////                .setDepartmentId(1L);
//        userMapper.insert(user);
//    }
//
//}
