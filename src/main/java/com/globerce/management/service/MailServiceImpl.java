package com.globerce.management.service;

import com.globerce.management.entity.system.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;


@Transactional
@PropertySource({"classpath:app.properties"})
@Component
@DependsOn(value = {"javaMailSender","messageSource"})
public class MailServiceImpl implements MailService {


    private JavaMailSender mailSender;
    private MessageSource messageSource;

    @Value("${appURL}")
    private String appURL;

    @Autowired
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Autowired
    public void setMailSender(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void trySendMessage(SimpleMailMessage message) {
        mailSender.send(message);
    }



    public void sendVerificationToken(User user, String callBackURL, Locale locale) {
        String subject = messageSource.getMessage("mail.subject.vfToken", null, locale);
        String message = messageSource.getMessage("mail.message.user.confirm", new Object[]{appURL,callBackURL}, locale);
        String sender = messageSource.getMessage("mail.sender", null, locale);
        SimpleMailMessage mailMessage = generateMessage(subject, user.getLogin(), message, sender);
        mailSender.send(mailMessage);
    }


    private SimpleMailMessage generateMessage(String subject, String recipient, String message, String sender) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(recipient);
        mailMessage.setSubject(subject);
        mailMessage.setFrom(sender != null ? sender : "");
        mailMessage.setText(message);
        return mailMessage;
    }

}
