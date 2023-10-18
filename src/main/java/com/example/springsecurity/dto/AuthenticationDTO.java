package com.example.springsecurity.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthenticationDTO {
    @NotEmpty(message = "Username should not be empty!")
    @Size(min = 2, max = 100, message = "Username should be 2 to 100 characters!")
    private String username;
    private String password;

    public AuthenticationDTO() {
    }

    public AuthenticationDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
