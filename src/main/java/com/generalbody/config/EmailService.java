package com.generalbody.config;

import java.util.Base64;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.generalbody.entity.User;

@Service
public class EmailService {

	private final TemplateEngine templateEngine;

    private final JavaMailSender javaMailSender;

    public EmailService(TemplateEngine templateEngine, JavaMailSender javaMailSender) {
        this.templateEngine = templateEngine;
        this.javaMailSender = javaMailSender;
    }

    public String sendMail(User user) throws MessagingException {
        String base64ImageData = Base64.getEncoder().encodeToString(user.getPhoto());
        user.setImageData(base64ImageData);
        Context context = new Context();
        context.setVariable("user", user);
        String process = templateEngine.process("emails/registerEmail", context);
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
        helper.setSubject("LIAFI General Council Meeting Registration Confirmation");
        helper.setText(process, true);
        helper.setTo(user.getEmail());
        javaMailSender.send(mimeMessage);
        return "Sent";
    }	
    
}
