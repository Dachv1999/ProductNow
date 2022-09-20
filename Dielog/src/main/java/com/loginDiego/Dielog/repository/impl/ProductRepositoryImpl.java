package com.loginDiego.Dielog.repository.impl;


import com.loginDiego.Dielog.models.Product;
import com.loginDiego.Dielog.repository.ProductRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

public class ProductRepositoryImpl implements ProductRepository {

    //Se tiene que importar
    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Product> getAll() {
        String query = "SELECT p FROM Product p";
        return em.createQuery(query)
                .getResultList();
    }

    @Override
    public Optional<Product> getById(Long productId) {
        String query = "SELECT p FROM Product p WHERE p.id = :id";
        return em.createQuery(query, Product.class)
                .setParameter("id",productId)
                .getResultList()
                .stream().findFirst();
    }

    @Override
    public Optional<Product> create(Product product) {
        return Optional.of(em.merge(product));
    }

    @Override
    public boolean delete(Long productId) {
        Product productDelete = em.find(Product.class, productId);
        em.remove(productDelete);
        return true;
    }
}
