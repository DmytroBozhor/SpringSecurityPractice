package com.example.springsecurity.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.UniqueElements;

@Entity
@Table(name = "person")
@Getter
@Setter
@ToString
public class PersonEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "username")
    @NotEmpty(message = "Username should not be empty!")
    @Size(min = 2, max = 100, message = "Username should be 2 to 100 characters!")
    private String username;

    @Column(name = "year_of_birth")
    @Min(value = 1900, message = "Year of birth can not be below 1900!")
    private int yearOfBirth;

    @Column(name = "password")
    private String password;

    public PersonEntity() {
    }

    public PersonEntity(String username, int yearOfBirth, String password) {
        this.username = username;
        this.yearOfBirth = yearOfBirth;
        this.password = password;
    }
}
