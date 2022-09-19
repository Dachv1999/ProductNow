package com.loginDiego.Dielog.controllers.impl;

import com.loginDiego.Dielog.service.UserService;
import com.loginDiego.Dielog.utils.JWTUtil;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
@AllArgsConstructor
public class ProductControllerImpl {

    //properties
    private final UserService userService;
    private final JWTUtil jwtUtil;
    //
    
    
}
