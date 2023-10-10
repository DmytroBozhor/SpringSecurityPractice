package com.example.springsecurity.util;

import com.example.springsecurity.model.PersonEntity;
import com.example.springsecurity.service.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

@Component
public class PersonValidator implements Validator {

    private final PeopleService peopleService;

    @Autowired
    public PersonValidator(PeopleService peopleService) {
        this.peopleService = peopleService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return PersonEntity.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        PersonEntity person = (PersonEntity) target;
        Optional<PersonEntity> personByUsername = peopleService.findUserByUsername(person.getUsername());
        if (personByUsername.isPresent()) {
            errors.rejectValue("username", "", "This username is already used!");
        }
    }
}
