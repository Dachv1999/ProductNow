package com.loginDiego.Dielog.controllers;

import com.loginDiego.Dielog.models.User;
import com.sun.istack.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

public interface UserController {

    @GetMapping
    ResponseEntity<List<User>> getAll(@RequestHeader(value = "Authorization") String token);

    @GetMapping("/{userId}")
    ResponseEntity<User> getById(@RequestHeader(value = "Authorization") String token, @NotNull @PathVariable("userId") Long userId);

    @PostMapping
    ResponseEntity<User> create(@Valid @RequestBody User user);

    @DeleteMapping("/{userId}")
    ResponseEntity delete(@RequestHeader(value = "Authorization") String token, @NotNull @PathVariable("userId") Long userId);
}
