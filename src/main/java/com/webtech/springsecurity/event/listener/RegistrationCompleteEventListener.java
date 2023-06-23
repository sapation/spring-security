package com.webtech.springsecurity.event.listener;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.webtech.springsecurity.entity.User;
import com.webtech.springsecurity.event.RegistrationCompleteEvent;
import com.webtech.springsecurity.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class RegistrationCompleteEventListener implements 
    ApplicationListener<RegistrationCompleteEvent>{

        @Autowired
        private UserService userService;

    @Override
    public void onApplicationEvent(RegistrationCompleteEvent event) {
        //Create the verification token for the User with Link
        User user = event.getUser();
        String token = UUID.randomUUID().toString();
        userService.saveVerificationTokenForUser(token, user);
        //Send Mail to user

        String url = event.getApplicationUrl() + "/verifyRegistration?token="+token;

        log.info("Click the link to verify your accounts: {}", url);
    }
}
