package com.loginDiego.Dielog.repository;

import com.loginDiego.Dielog.models.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    List<Product> getAll();

    Optional<Product> getById(Long productId);

    Optional<Product> create(Product product);

    boolean delete(Long productId);
}
