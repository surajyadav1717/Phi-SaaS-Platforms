package com.dashboard.saas.service.emails;

import com.dashboard.saas.entities.EmailDelivery;
import com.dashboard.saas.enums.EmailStatus;
import com.dashboard.saas.repositories.EmailDeliveryRepository;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class EmailSender {

    private final JavaMailSender mailSender;

    private final EmailDeliveryRepository emailDeliveryRepository;


    public EmailSender(JavaMailSender mailSender, EmailDeliveryRepository emailDeliveryRepository) {
        this.mailSender = mailSender;
        this.emailDeliveryRepository = emailDeliveryRepository;
    }


    @Retryable(
            retryFor = MailException.class,
            maxAttempts = 3,
            backoff = @Backoff(
                    delay = 2000,
                    multiplier = 2
            )
    )

    public void send(
            String to,
            String subject,
            String message,
            Long deliveryId) {

        EmailDelivery delivery =
                emailDeliveryRepository
                        .findById(deliveryId)
                        .orElseThrow();

        delivery.setAttempts(
                delivery.getAttempts() + 1
        );

        emailDeliveryRepository.save(delivery);

        System.out.println(
                "EMAIL ATTEMPT: "
                        + delivery.getAttempts()
        );

        SimpleMailMessage mail =
                new SimpleMailMessage();

//        mail.setFrom(from);
        mail.setTo(to);
        mail.setSubject(subject);
        mail.setText(message);

        mailSender.send(mail);

        // SUCCESS
        delivery.setStatus(EmailStatus.SENT);
        delivery.setSentAt(LocalDateTime.now());

        emailDeliveryRepository.save(delivery);

        System.out.println(
                "EMAIL SENT SUCCESSFULLY - "
                        + Thread.currentThread().getName()
        );

    }

    @Recover
    public void recover(
            MailException e,
            String from,
            String to,
            String subject,
            String message,
            Long deliveryId) {

        EmailDelivery delivery =
                emailDeliveryRepository
                        .findById(deliveryId)
                        .orElseThrow();

        delivery.setStatus(EmailStatus.FAILED);
        delivery.setErrorMessage(e.getMessage());

        emailDeliveryRepository.save(delivery);

        System.out.println(
                "EMAIL FAILED AFTER 3 ATTEMPTS"
        );

        e.printStackTrace();
    }
}