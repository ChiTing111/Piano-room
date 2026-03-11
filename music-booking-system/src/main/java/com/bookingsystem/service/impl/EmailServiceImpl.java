package com.bookingsystem.service.impl;

import com.bookingsystem.model.EmailDetails;
import com.bookingsystem.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.io.File;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * 邮件服务实现类
 */
@Service
@Slf4j
//@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    @Autowired
    private  JavaMailSender mailSender;
    @Autowired
    private  SpringTemplateEngine templateEngine;
    
    @Value("${spring.mail.username}")
    private String fromEmail;
    
    @Override
    public boolean sendSimpleMail(String to, String subject, String content) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(to);
            message.setSubject(subject);
            message.setText(content);
            
            mailSender.send(message);
            log.info("简单邮件已发送至：{}", to);
            return true;
        } catch (Exception e) {
            log.error("发送简单邮件时出错: {}", e.getMessage(), e);
            return false;
        }
    }
    
    @Override
    public boolean sendAttachmentMail(String to, String subject, String content,
                                     String attachmentPath, String attachmentName) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            
            helper.setFrom(fromEmail);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content);
            
            FileSystemResource file = new FileSystemResource(new File(attachmentPath));
            helper.addAttachment(attachmentName, file);
            
            mailSender.send(message);
            log.info("带附件的邮件已发送至：{}", to);
            return true;
        } catch (MessagingException e) {
            log.error("发送带附件邮件时出错: {}", e.getMessage(), e);
            return false;
        }
    }
    
    @Override
    public boolean sendHtmlMail(String to, String subject, String htmlContent) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            
            helper.setFrom(fromEmail);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlContent, true);
            
            mailSender.send(message);
            log.info("HTML邮件已发送至：{}", to);
            return true;
        } catch (MessagingException e) {
            log.error("发送HTML邮件时出错: {}", e.getMessage(), e);
            return false;
        }
    }
    
    @Override
    public boolean sendTemplateMail(String to, String subject, String templateName, 
                                   Map<String, Object> variables) {
        try {
            Context context = new Context();
            context.setVariables(variables);
            String htmlContent = templateEngine.process(templateName, context);
            
            return sendHtmlMail(to, subject, htmlContent);
        } catch (Exception e) {
            log.error("发送模板邮件时出错: {}", e.getMessage(), e);
            return false;
        }
    }
    
    @Override
    public boolean sendBulkMail(String[] recipients, String subject, String content) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(recipients);
            message.setSubject(subject);
            message.setText(content);
            
            mailSender.send(message);
            log.info("批量邮件已发送至{}位收件人", recipients.length);
            return true;
        } catch (Exception e) {
            log.error("发送批量邮件时出错: {}", e.getMessage(), e);
            return false;
        }
    }
    
    @Override
    @Async
    public boolean sendAsyncMail(EmailDetails emailDetails) {
        CompletableFuture.runAsync(() -> {
            try {
                MimeMessage message = mailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(message, true);
                
                helper.setFrom(fromEmail);
                helper.setTo(emailDetails.getTo());
                
                if (emailDetails.getCc() != null && emailDetails.getCc().length > 0) {
                    helper.setCc(emailDetails.getCc());
                }
                
                if (emailDetails.getBcc() != null && emailDetails.getBcc().length > 0) {
                    helper.setBcc(emailDetails.getBcc());
                }
                
                helper.setSubject(emailDetails.getSubject());
                
                // 处理模板邮件
                if (emailDetails.getTemplateName() != null && !emailDetails.getTemplateName().isEmpty()) {
                    Context context = new Context();
                    context.setVariables(emailDetails.getTemplateVariables());
                    String htmlContent = templateEngine.process(emailDetails.getTemplateName(), context);
                    helper.setText(htmlContent, true);
                } else {
                    helper.setText(emailDetails.getContent(), emailDetails.isHtml());
                }
                
                // 处理附件
                if (emailDetails.getAttachmentPaths() != null && emailDetails.getAttachmentNames() != null) {
                    for (int i = 0; i < emailDetails.getAttachmentPaths().length; i++) {
                        FileSystemResource file = new FileSystemResource(new File(emailDetails.getAttachmentPaths()[i]));
                        helper.addAttachment(emailDetails.getAttachmentNames()[i], file);
                    }
                }
                
                mailSender.send(message);
                log.info("异步邮件已发送至：{}", emailDetails.getTo());
            } catch (MessagingException e) {
                log.error("发送异步邮件时出错: {}", e.getMessage(), e);
            }
        });
        
        return true;
    }
}