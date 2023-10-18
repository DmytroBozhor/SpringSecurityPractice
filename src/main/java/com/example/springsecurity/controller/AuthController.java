package com.example.springsecurity.controller;

import com.example.springsecurity.dto.AuthenticationDTO;
import com.example.springsecurity.dto.PersonDTO;
import com.example.springsecurity.model.PersonEntity;
import com.example.springsecurity.security.JWTUtil;
import com.example.springsecurity.service.PeopleService;
import com.example.springsecurity.util.PersonValidator;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final PeopleService peopleService;
    private final PersonValidator personValidator;
    private final JWTUtil jwtUtil;
    private final ModelMapper modelMapper;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthController(PeopleService peopleService, PersonValidator personValidator, JWTUtil jwtUtil, ModelMapper modelMapper, AuthenticationManager authenticationManager) {
        this.peopleService = peopleService;
        this.personValidator = personValidator;
        this.jwtUtil = jwtUtil;
        this.modelMapper = modelMapper;
        this.authenticationManager = authenticationManager;
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
    public Map<String, String> registerPerson(@RequestBody @Valid PersonDTO personDTO,
                                              BindingResult bindingResult) {
        PersonEntity person = convertToPerson(personDTO);

        personValidator.validate(person, bindingResult);

        if (bindingResult.hasErrors()) {
            return Map.of("message", "Error");
        }

        peopleService.save(person);

        String token = jwtUtil.generateToken(person.getUsername());
        return Map.of("jwt-token", token);
    }

    @PostMapping("/login")
    public Map<String, String> performLogin(@RequestBody AuthenticationDTO authenticationDTO) {
        UsernamePasswordAuthenticationToken authInputToken = new UsernamePasswordAuthenticationToken(
                authenticationDTO.getPassword(), authenticationDTO.getPassword());

        try {
            authenticationManager.authenticate(authInputToken);
        } catch (AuthenticationException e) {
            return Map.of("message", "Incorrect credentials");
        }

        String token = jwtUtil.generateToken(authenticationDTO.getUsername());
        return Map.of("jwt-token", token);
    }

    public PersonEntity convertToPerson(PersonDTO personDTO) {
        return modelMapper.map(personDTO, PersonEntity.class);
    }
}
