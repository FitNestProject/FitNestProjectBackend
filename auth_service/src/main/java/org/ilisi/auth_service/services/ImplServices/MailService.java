package org.ilisi.auth_service.services.ImplServices;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;


import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class MailService {
    private JavaMailSender javaMailSender;
    private SpringTemplateEngine springTemplateEngine;

    @Value("${spring.mail.from}")
    private String from;

    public void sendVerificationEmail(String toEmail, String verificationToken) throws MessagingException {
        String verificationLink = "https://yourapp.com/verify-email?token=" + verificationToken;
        String subject = "Verify Your Email Address";

        Map<String, Object> variables = Map.of(
                "verificationLink", verificationLink,
                "buttonText", "Verify My Email",
                "headerText", "Confirm Your Email Address",
                "messageText", "Thank you for signing up on our platform. Please confirm your email address by clicking the button below."
        );

        sendTemplateMail(toEmail, subject, "verification-email-template", variables);
    }

    public void sendSimpleMailMessage(String to, String subject, String text) {
        try {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setFrom(from);
            simpleMailMessage.setTo(to);
            simpleMailMessage.setSubject(subject);
            simpleMailMessage.setText(text);
            this.javaMailSender.send(simpleMailMessage);
        } catch (Exception exception) {
            log.error("Failed to send email", exception);
        }
    }

    public void sendTemplateMail(String to, String subject, String template, Map<String, Object> variables) throws MessagingException {
        MimeMessage mimeMessage = this.javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

        helper.setFrom(from);
        helper.setTo(to);
        helper.setSubject(subject);

        Context context = new Context();
        context.setVariables(variables);

        String html = this.springTemplateEngine.process(template, context);

        helper.setText(html, true);
        this.javaMailSender.send(mimeMessage);
    }
}