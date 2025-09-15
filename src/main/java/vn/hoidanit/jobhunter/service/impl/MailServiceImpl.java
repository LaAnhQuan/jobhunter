package vn.hoidanit.jobhunter.service.impl;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;


import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;
import vn.hoidanit.jobhunter.service.MailService;

import java.nio.charset.StandardCharsets;

@Service
public class MailServiceImpl implements MailService {
    private final JavaMailSender javaMailSender;
    private final SpringTemplateEngine templateEngine;

    public MailServiceImpl(JavaMailSender mailSender,
                           JavaMailSender javaMailSender,
                           SpringTemplateEngine templateEngine) {
        this.javaMailSender = javaMailSender;
        this.templateEngine = templateEngine;
    }

    public void sendSimpleEmail(){
        try {
            SimpleMailMessage msg = new SimpleMailMessage();
            msg.setTo("nitnit072004@gmail.com");
            msg.setSubject("Testing from Spring Boot");
            msg.setText("Hello World from Spring Boot Email");
            this.javaMailSender.send(msg);
            System.out.println(">>> Email sent successfully");
        } catch (Exception e) {
            System.out.println(">>> Email send error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void sendEmailSync(String to, String subject, String content, boolean isMultipart, boolean isHtml) {
        // Prepare message using a Spring helper
        MimeMessage mimeMessage = this.javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, isMultipart, StandardCharsets.UTF_8.name());
            message.setTo(to);
            message.setSubject(subject);
            message.setText(content, isHtml);
            this.javaMailSender.send(mimeMessage);
        } catch (MailException | MessagingException e) {
            System.out.println("ERROR SEND EMAIL: " + e);
        }
    }

    public void sendEmailFromTemplateSync(String to, String subject, String templateName) {
        Context context = new Context();
        String content = this.templateEngine.process(templateName, context);
        this.sendEmailSync(to, subject, content, false, true);
    }


}
