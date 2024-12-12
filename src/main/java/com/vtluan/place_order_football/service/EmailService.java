package com.vtluan.place_order_football.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender javaMailSender;

    private final TemplateEngine templateEngine;

    public void sendEmail(String email, Object data) throws MessagingException {
        Context context = new Context();
        context.setVariable("Orders", data);

        // Render nội dung email từ template
        String htmlContent = templateEngine.process("sendemail", context);

        // Tạo và gửi email
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

        helper.setTo(email);
        helper.setSubject("Thông tin đặt sân");
        helper.setText(htmlContent, true);

        javaMailSender.send(mimeMessage);
    }
}
