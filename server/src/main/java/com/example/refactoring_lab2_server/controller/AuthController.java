package com.example.refactoring_lab2_server.controller;

import com.example.refactoring_lab2_server.services.TurtleService;
import com.example.refactoring_lab2_server.entities.User;
import com.example.refactoring_lab2_server.entities.UserDto;
import com.example.refactoring_lab2_server.services.UserDtoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserDtoService userDTOService;

    private final TurtleService turtleService;

    @PostMapping("/registration")
    public void addUser(@RequestBody UserDto user, HttpServletRequest request) throws Exception {
        final User registeredUser = userDTOService.register(user);
        turtleService.initStateForUser(registeredUser);
        request.login(user.getUsername(), user.getPassword());
    }

    @PostMapping("/login")
    public void login(@RequestBody UserDto user, HttpServletRequest request) throws Exception {
        if (userDTOService.canLogin(user.getUsername(), user.getPassword())) {
            request.login(user.getUsername(), user.getPassword());
        }
    }
}
