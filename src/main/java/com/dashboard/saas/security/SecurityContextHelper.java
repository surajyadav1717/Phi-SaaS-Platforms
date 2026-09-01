package com.dashboard.saas.security;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public  class SecurityContextHelper {


    public static Authentication getAuthentication() {

        return SecurityContextHolder
                .getContext()
                .getAuthentication();
    }

    public static CustomUserPrincipal getCurrentUser() {

        Authentication authentication =
                getAuthentication();

        if (authentication == null ||
                !authentication.isAuthenticated()) {

            throw new IllegalStateException(
                    "User is not authenticated"
            );
        }

        Object principal =
                authentication.getPrincipal();

        if (!(principal instanceof CustomUserPrincipal)) {

            throw new IllegalStateException(
                    "Invalid authenticated principal"
            );
        }

        return (CustomUserPrincipal) principal;
    }

    public static Long getCurrentUserId() {

        return getCurrentUser().getUserId();
    }

    public String getCurrentUserEmail() {
        return getCurrentUser().getEmail();
    }
}
