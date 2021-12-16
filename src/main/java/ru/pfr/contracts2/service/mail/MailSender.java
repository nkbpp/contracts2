package ru.pfr.contracts2.service.mail;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import ru.pfr.contracts2.entity.log.Logi;
import ru.pfr.contracts2.service.log.LogiService;

@Service
@RequiredArgsConstructor
public class MailSender {

    @Value("${spring.mail.username}")
    private String username;

    private final LogiService logiService;

    private final JavaMailSender javaMailSender;

    public void send(String emailTo, String subject, String message){
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

        simpleMailMessage.setFrom(username);
        simpleMailMessage.setTo(emailTo);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(message);

        javaMailSender.send(simpleMailMessage);

        logiService.save(new Logi("this","Mail","Почта отправлена пользователю " + username +
                " на email: " + emailTo +
                " с текстом: " + message));
    }
}
