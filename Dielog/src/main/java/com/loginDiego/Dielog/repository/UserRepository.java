package com.loginDiego.Dielog.repository;

import com.loginDiego.Dielog.models.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    List<User> getAll();
    Optional<User> getById(Long userId);
    Optional<User> create(User user);
    boolean delete(Long userId);
    User getValidUser(User user);

}
