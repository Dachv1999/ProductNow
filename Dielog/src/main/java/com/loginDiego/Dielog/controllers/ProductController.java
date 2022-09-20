package com.loginDiego.Dielog.controllers;

import com.loginDiego.Dielog.models.Product;
import com.sun.istack.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

public interface ProductController {
    @GetMapping
    ResponseEntity<List<Product>> getAll(@RequestHeader(value = "Authorization") String token);

    @GetMapping("/{productId}")
    ResponseEntity<Product> getById(@RequestHeader(value = "Authorization") String token, @NotNull @PathVariable("productId") Long productId);

    @PostMapping
    ResponseEntity<Product> create(@Valid @RequestBody Product product);

    @DeleteMapping("/{productId}")
    ResponseEntity delete(@RequestHeader(value = "Authorization") String token, @NotNull @PathVariable("productId") Long productId);
}
