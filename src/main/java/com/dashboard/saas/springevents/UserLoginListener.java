package com.dashboard.saas.springevents;

import com.dashboard.saas.service.AuditLogService;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class UserLoginListener {

    private final AuditLogService auditLogService;

    public UserLoginListener(AuditLogService auditLogService) {
        this.auditLogService = auditLogService;
    }


    @EventListener
    public void handleUserLoginListner(UserLoggedInEvent userLoggedInEvent){

        auditLogService.saveAuditLog(
                userLoggedInEvent.getUserId(),
                "User-Logged",
                "User-Login SuccessFully",
                userLoggedInEvent.getIpAddress()
        );
        System.out.println("First Listner User Logged In Event Received :- "+userLoggedInEvent.getEmail());
    }
}
