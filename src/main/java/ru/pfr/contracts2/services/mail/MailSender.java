package ru.pfr.contracts2.services.mail;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import ru.pfr.contracts2.entity.log.entity.Logi;
import ru.pfr.contracts2.services.log.LogiService;

@Service
@RequiredArgsConstructor
public class MailSender {

    @Value("${spring.mail.username}")
    private String username;

    private final LogiService logiService;

    private final JavaMailSender javaMailSender;

    public void send(String emailTo, String subject, String message) {

        try {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

            simpleMailMessage.setFrom(username);
            simpleMailMessage.setTo(emailTo);
            simpleMailMessage.setSubject(subject);
            simpleMailMessage.setText(message);

            javaMailSender.send(simpleMailMessage);

            logiService.save(new Logi("this", "Mail", "Почта отправлена пользователю " + username +
                    " на email: " + emailTo +
                    " с текстом: " + message));
        } catch (Exception e) {
            logiService.save(new Logi("this", "MailError", "Отправить письмо не удалось " + username +
                    " на email: " + emailTo +
                    " с текстом: " + message +
                    " ошибка " + e));
        }

    }
}
