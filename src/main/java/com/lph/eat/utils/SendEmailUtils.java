package com.lph.eat.utils;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

/**
 * 邮箱验证码发送工具类
 */
@Component
public class SendEmailUtils {

    // 注入发送邮件的bean
    private static JavaMailSender mailSender;

    /**
     * 发送邮件验证码
     *
     * @param userEmail 收件人邮件（用户的）
     * @param code      验证码
     */
    public static void sendEmail(String userEmail, String code) {
        try {
            //创建邮件对象
            SimpleMailMessage message = new SimpleMailMessage();
            //邮件标题
            message.setSubject("登录验证码");
            //邮件内容
            message.setText("您的验证码为：" + code + "，请妥善保管！");
            //邮件发送的对象
            message.setTo(userEmail);
            //发送邮件的邮箱
            message.setFrom("lskoudai@foxmail.com");
            //发送邮件
            mailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Autowired
    public void setMailSender(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }
}
