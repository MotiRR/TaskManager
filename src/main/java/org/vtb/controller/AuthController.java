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
import org.vtb.service.UserService;

import javax.validation.Valid;


@RestController
public class AuthController {
    @Autowired
    private UserService userService;
    @Autowired
    private JwtProvider jwtProvider;

    @PostMapping("/register")
    public AuthResponse registerUser(@RequestBody @Valid RegistrationRequest registrationRequest) {
        User user = new User();
        user.setPassword(registrationRequest.getPassword());
        user.setLogin(registrationRequest.getLogin());
        userService.saveUser(user);
        String token = jwtProvider.generateToken(user.getLogin());
        return new AuthResponse("200", token);
    }

    @PostMapping("/auth")
    public AuthResponse auth(@RequestBody AuthRequest request) {
        User userEntity = userService.findByLoginAndPassword(request.getLogin(), request.getPassword());
        if(userEntity == null) return new AuthResponse("not user", "");
        String token = jwtProvider.generateToken(userEntity.getLogin());
        return new AuthResponse("200", token);
    }
}
