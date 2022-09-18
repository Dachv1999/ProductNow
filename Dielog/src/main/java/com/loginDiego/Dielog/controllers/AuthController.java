package com.loginDiego.Dielog.controllers;

import com.loginDiego.Dielog.models.User;
import com.loginDiego.Dielog.repository.UserRepository;
import com.loginDiego.Dielog.utils.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/login")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JWTUtil jwtUtil;

    @PostMapping
    public String login(@RequestBody User user){
        User userLogeado = userRepository.getValidUser(user);
        if(userLogeado != null){
            return jwtUtil.create(String.valueOf(userLogeado.getId()), userLogeado.getEmail());
        }else{
            return "FAIL";
        }
    }

}