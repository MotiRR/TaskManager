package org.vtb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.vtb.config.jwt.JwtProvider;
import org.vtb.controller.classes.AuthRequest;
import org.vtb.controller.classes.AuthResponse;
import org.vtb.controller.classes.RegistrationRequest;
import org.vtb.entity.User;
import org.vtb.exception.RegistrationException;
import org.vtb.service.UserService;

import javax.validation.Valid;
import java.util.List;


@RestController
public class AuthController {
    @Autowired
    private UserService userService;
    @Autowired
    private JwtProvider jwtProvider;

    @PostMapping("/register")
    public AuthResponse registerUser(@RequestBody @Valid RegistrationRequest registrationRequest) {
        User user;
        try {
            user = userService.createUser(registrationRequest);
        } catch (RegistrationException ex) {
            return new AuthResponse(401, "", List.of(ex.getMessage()));
        }
        String token = jwtProvider.generateToken(user.getLogin());
        return new AuthResponse(201, token, List.of("Зарегестрирован"));
    }

    @PostMapping("/auth")
    public AuthResponse auth(@RequestBody AuthRequest request) {
        User user = userService.findByLoginAndPassword(request.getLogin(), request.getPassword());
        if(user == null) return new AuthResponse(401, "", List.of("Пользователь не найден"));
        String token = jwtProvider.generateToken(user.getLogin());
        return new AuthResponse(200, token, List.of("Авторизован"));
    }
}
