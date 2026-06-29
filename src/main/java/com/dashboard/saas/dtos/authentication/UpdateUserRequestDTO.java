package com.dashboard.saas.dtos.authentication;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UpdateUserRequestDTO {


    @NotBlank(message = "Name is required")
    @Size(min = 1, max = 50, message = "Name must be between 3 and 50 characters")
    private String name;


    @NotBlank(message = "Email is required")
    @Email(message = "Enter a valid email address")
    private String email;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
