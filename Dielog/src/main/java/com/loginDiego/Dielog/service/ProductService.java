package com.loginDiego.Dielog.service;


import com.loginDiego.Dielog.models.Product;
import com.loginDiego.Dielog.models.User;
import com.loginDiego.Dielog.repository.ProductRepository;
import com.loginDiego.Dielog.repository.UserRepository;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class ProductService {

    //properties
    private final ProductRepository productRepository;
    //

    public List<Product> getAll(){

        return productRepository.getAll();
    }

    public Optional<Product> getById(final Long productId){

        return productRepository.getById(productId);
    }

    public Optional<Product>  create(final Product product){

        return productRepository.create(product);
    }

    public boolean delete (final Long productId) {

        return productRepository.delete(productId) ? true : false;
    }
}
