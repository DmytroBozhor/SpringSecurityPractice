package com.example.springsecurity.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PersonDTO {
    @NotEmpty(message = "Username should not be empty!")
    @Size(min = 2, max = 100, message = "Username should be 2 to 100 characters!")
    private String username;

    @Min(value = 1900, message = "Year of birth can not be below 1900!")
    private int yearOfBirth;

    private String password;

    public PersonDTO() {
    }

    public PersonDTO(String username, int yearOfBirth, String password) {
        this.username = username;
        this.yearOfBirth = yearOfBirth;
        this.password = password;
    }
}
