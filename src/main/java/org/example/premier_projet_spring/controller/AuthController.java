package org.example.premier_projet_spring.controller;

import jakarta.validation.Valid;
import org.example.premier_projet_spring.dao.UserDao;
import org.example.premier_projet_spring.dto.EmailValidationDto;
import org.example.premier_projet_spring.model.User;
import org.example.premier_projet_spring.security.AppUserDetails;
import org.example.premier_projet_spring.security.ISecurityUtils;
import org.example.premier_projet_spring.security.SecurityUtils;
import org.example.premier_projet_spring.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.UUID;

@CrossOrigin
@RestController
public class AuthController {

    private final EmailService emailService;
    protected UserDao userDao;
    protected PasswordEncoder passwordEncoder;
    protected AuthenticationProvider authenticationProvider;
    protected ISecurityUtils securityUtils;

    @Autowired
    public AuthController(UserDao userDao, PasswordEncoder passwordEncoder, AuthenticationProvider authenticationProvider, SecurityUtils securityUtils, EmailService emailService) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
        this.authenticationProvider = authenticationProvider;
        this.securityUtils = securityUtils;
        this.emailService = emailService;
    }


    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody @Validated(User.ValidRegister.class) User user) {
//        user.setRole(Role.USER);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        String emailValidationToken = UUID.randomUUID().toString();


        user.setValidEmailToken(emailValidationToken);

        userDao.save(user);
        emailService.sendEmailValidationToken(user.getEmail(), emailValidationToken);
        //Masque le mot de passe dans la réponse
        user.setPassword(null);
        user.setValidEmailToken(null);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody @Valid User user) {
        try {
            AppUserDetails userDetails = (AppUserDetails) authenticationProvider.authenticate(
                            new UsernamePasswordAuthenticationToken(
                                    user.getEmail(),
                                    user.getPassword()))
                    .getPrincipal();
            return new ResponseEntity<>(securityUtils.generateToken(userDetails), HttpStatus.OK);

        } catch (AuthenticationException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }

    }

    @PostMapping("/validate-email")
    public ResponseEntity<User> validateEmail(@RequestBody EmailValidationDto emailValidationDto) {

        Optional<User> user = userDao.findByValidEmailToken(emailValidationDto.getToken());

        if (user.isPresent()) {
            user.get().setValidEmailToken(null);
            userDao.save(user.get());
            return new ResponseEntity<>(user.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);

    }
}

