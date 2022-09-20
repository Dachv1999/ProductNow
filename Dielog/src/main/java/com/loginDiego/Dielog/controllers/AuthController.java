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
            return jwtUtil.create(String.valueOf(userLogeado.getId()), userLogeado.getType());
        }else{
            return "FAIL";
        }
    }

    public boolean validateTokenUser(String token){
        String userId = jwtUtil.getKey(token);
        return userId != null;
    }

    public boolean validateTokenAdmin(String token){
        String userId = jwtUtil.getKey(token);
        String admin = jwtUtil.getValue(token);
        return userId != null || admin != null;
    }

}
