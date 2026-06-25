package com.dashboard.saas.springevents;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class SecondUserLoginListener {


    @EventListener
    public void handleUserLoggedIn(UserLoggedInEvent userLoggedInEvent){

        System.out.println("Second Welcome Email Sent to  "+ userLoggedInEvent.getEmail());
    }
}
