package com.bookingsystem.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * 邮件详情模型
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmailDetails {
    
    /**
     * 收件人邮箱
     */
    private String to;
    
    /**
     * 抄送人
     */
    private String[] cc;
    
    /**
     * 密送人
     */
    private String[] bcc;
    
    /**
     * 邮件主题
     */
    private String subject;
    
    /**
     * 邮件内容
     */
    private String content;
    
    /**
     * 是否HTML格式
     */
    private boolean isHtml;
    
    /**
     * 附件路径
     */
    private String[] attachmentPaths;
    
    /**
     * 附件名称
     */
    private String[] attachmentNames;
    
    /**
     * 邮件模板名称
     */
    private String templateName;
    
    /**
     * 模板变量
     */
    private Map<String, Object> templateVariables;
}