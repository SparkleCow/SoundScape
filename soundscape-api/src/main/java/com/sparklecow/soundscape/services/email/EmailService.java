package com.sparklecow.soundscape.services.email;

import com.sparklecow.soundscape.models.email.EmailTemplateName;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/*This class will allow to send a validation code to the registration email beforehand*/
@Service
@RequiredArgsConstructor
public class EmailService{

    /*This is using JavaMail and Thymeleaf for the email service*/
    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;

    @Async
    public void sendEmail(String to,
                          String subject,
                          String username,
                          String activationCode,
                          String activationUrl,
                          EmailTemplateName emailTemplateName) throws MessagingException {

        String templateName;
        if(emailTemplateName==null){
            templateName = "confirm-email";
        }else{
            templateName = emailTemplateName.getTemplateName();
        }

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,
                MimeMessageHelper.MULTIPART_MODE_MIXED,
                StandardCharsets.UTF_8.name());

        Map<String,Object> properties = new HashMap<>();
        properties.put("username",username);
        properties.put("activation_code", activationCode);
        properties.put("confirmationUrl", activationUrl);

        Context context = new Context();
        context.setVariables(properties);

        helper.setFrom("cjelcazador300@gmail.com");
        helper.setTo(to);
        helper.setSubject(subject);

        String template = templateEngine.process(templateName, context);
        helper.setText(template,true);

        mailSender.send(mimeMessage);
    }
}