package com.bookingsystem.service;



import com.bookingsystem.model.EmailDetails;

import java.util.Map;

/**
 * 邮件服务接口
 */
public interface EmailService {
    
    /**
     * 发送简单邮件
     * 
     * @param to 收件人
     * @param subject 主题
     * @param content 内容（纯文本）
     * @return 是否发送成功
     */
    boolean sendSimpleMail(String to, String subject, String content);
    
    /**
     * 发送带附件的邮件
     * 
     * @param to 收件人
     * @param subject 主题
     * @param content 内容（纯文本）
     * @param attachmentPath 附件路径
     * @param attachmentName 附件名称
     * @return 是否发送成功
     */
    boolean sendAttachmentMail(String to, String subject, String content, 
                              String attachmentPath, String attachmentName);
    
    /**
     * 发送HTML格式邮件
     * 
     * @param to 收件人
     * @param subject 主题
     * @param htmlContent HTML内容
     * @return 是否发送成功
     */
    boolean sendHtmlMail(String to, String subject, String htmlContent);
    
    /**
     * 使用模板发送邮件
     * 
     * @param to 收件人
     * @param subject 主题
     * @param templateName 模板名称
     * @param variables 模板变量
     * @return 是否发送成功
     */
    boolean sendTemplateMail(String to, String subject, String templateName, Map<String, Object> variables);
    
    /**
     * 发送批量邮件（群发）
     * 
     * @param recipients 收件人列表
     * @param subject 主题
     * @param content 内容
     * @return 是否发送成功
     */
    boolean sendBulkMail(String[] recipients, String subject, String content);
    
    /**
     * 异步发送邮件
     * 
     * @param emailDetails 邮件详情
     * @return 是否加入发送队列成功
     */
    boolean sendAsyncMail(EmailDetails emailDetails);
}