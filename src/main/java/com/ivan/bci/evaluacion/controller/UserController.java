package com.ivan.bci.evaluacion.controller;

import com.ivan.bci.evaluacion.model.UserRequest;
import com.ivan.bci.evaluacion.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class UserController
{

    @Autowired
    private UserService userService;

    @PostMapping
    public String newUser(@RequestBody UserRequest userRequest)
    {
        return userService.addUser(userRequest);
    }

}
