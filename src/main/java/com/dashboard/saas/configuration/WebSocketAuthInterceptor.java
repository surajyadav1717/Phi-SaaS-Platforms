package com.dashboard.saas.configuration;

import com.dashboard.saas.security.JwtTokenProvider;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.util.Collections;
public class WebSocketAuthInterceptor implements ChannelInterceptor {

    private final JwtTokenProvider jwtTokenProvider ;

    public WebSocketAuthInterceptor(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public Message<?> preSend(
            Message<?> message,
            MessageChannel channel) {

        StompHeaderAccessor accessor =
                MessageHeaderAccessor.getAccessor(
                        message,
                        StompHeaderAccessor.class
                );

        if (accessor != null &&
                StompCommand.CONNECT.equals(accessor.getCommand())) {

            String authHeader =
                    accessor.getFirstNativeHeader("Authorization");

            if (authHeader == null ||
                    !authHeader.startsWith("Bearer ")) {

                throw new IllegalArgumentException(
                        "Missing Authorization header"
                );
            }

            String token =
                    authHeader.substring(7);

            Long username =
                    jwtTokenProvider.userIdFromToken(token);

            Authentication authentication =
                    new UsernamePasswordAuthenticationToken(
                            username,
                            null,
                            Collections.emptyList()
                    );

            accessor.setUser(authentication);
        }

        return message;
    }
}
