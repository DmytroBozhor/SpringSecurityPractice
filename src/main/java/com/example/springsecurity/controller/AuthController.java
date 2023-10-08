package com.example.springsecurity.controller;

import com.example.springsecurity.model.PersonEntity;
import com.example.springsecurity.service.PersonService;
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

    private final PersonService personService;

    @Autowired
    public AuthController(PersonService personService) {
        this.personService = personService;
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

        if (bindingResult.hasErrors()){
            return "auth/register";
        }

        personService.save(person);
        return "auth/register";
    }
}
