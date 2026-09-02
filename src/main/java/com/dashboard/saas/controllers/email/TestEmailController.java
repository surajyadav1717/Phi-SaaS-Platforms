package com.dashboard.saas.controllers.email;


import com.dashboard.saas.security.SecurityContextHelper;
import com.dashboard.saas.service.emails.EmailService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/test-email")
public class TestEmailController {


    private final EmailService emailService;

    public TestEmailController(EmailService emailService) {
        this.emailService = emailService;
    }


    @PostMapping("/email")
    public String sendTestEmail() {

        long userId= SecurityContextHelper.getCurrentUserId();

        emailService.sendEmail(
                "your-email@gmail.com",
                "Test Email",
                "Hello! This is a test email from Spring Boot.",
                userId

        );

        return "Email sent successfully";
    }
}
