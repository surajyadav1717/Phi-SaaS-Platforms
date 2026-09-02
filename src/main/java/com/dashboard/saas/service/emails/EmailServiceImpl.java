package com.dashboard.saas.service.emails;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {


    private final JavaMailSender mailSender;

    public EmailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }


    @Override
    @Async("emailTaskExecutor")
    public void sendEmail(String to, String subject, String message, Long userId)  {

        try {

            SimpleMailMessage mail = new SimpleMailMessage();

            mail.setTo(to);
            mail.setSubject(subject);
            mail.setText(message);

            mailSender.send(mail);

            System.out.println("EMAIL SENT SUCCESSFULLY");

        } catch (Exception e) {

            System.out.println("EMAIL FAILED");
            e.printStackTrace();

            throw e;
        }
    }


}
