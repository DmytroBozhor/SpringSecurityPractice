package com.example.springsecurity.controller;

import com.example.springsecurity.model.PersonEntity;
import com.example.springsecurity.service.PeopleService;
import com.example.springsecurity.util.PersonValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final PeopleService peopleService;
    private final PersonValidator personValidator;

    @Autowired
    public AuthController(PeopleService peopleService, PersonValidator personValidator) {
        this.peopleService = peopleService;
        this.personValidator = personValidator;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "auth/login";
    }

    @GetMapping("/register")
    public String registerPage(@ModelAttribute("person") PersonEntity person) {
        return "auth/register";
    }

    @PostMapping("/register")
    public String registerPerson(@ModelAttribute("person") @Valid PersonEntity person,
                                 BindingResult bindingResult) {

        personValidator.validate(person, bindingResult);

        if (bindingResult.hasErrors()){
            return "auth/register";
        }

        peopleService.save(person);
        return "redirect:auth/login";
    }
}
