package com.dashboard.saas.service.emails;
import com.dashboard.saas.entities.EmailDelivery;
import com.dashboard.saas.enums.EmailStatus;
import com.dashboard.saas.repositories.EmailDeliveryRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class EmailServiceImpl implements EmailService {


    private final EmailSender mailSender;

    private final EmailDeliveryRepository emailDeliveryRepository;

    public EmailServiceImpl(EmailSender mailSender, EmailDeliveryRepository emailDeliveryRepository) {
        this.mailSender = mailSender;
        this.emailDeliveryRepository = emailDeliveryRepository;
    }


    @Async("emailTaskExecutor")
    public void sendEmail(
            String to,
            String subject,
            String message,
            Long userId) {

        EmailDelivery delivery = new EmailDelivery();

        delivery.setUserId(userId);
        delivery.setRecipient(to);
        delivery.setSubject(subject);
        delivery.setMessage(message);
        delivery.setStatus(EmailStatus.PENDING);
        delivery.setAttempts(0);
        delivery.setCreatedAt(LocalDateTime.now());

        delivery = emailDeliveryRepository.save(delivery);

        try {

            mailSender.send(
                    to,
                    subject,
                    message,
                    delivery.getId()
            );

        } catch (Exception e) {

            System.out.println("FINAL EMAIL FAILURE");
            e.printStackTrace();
        }
    }

}
