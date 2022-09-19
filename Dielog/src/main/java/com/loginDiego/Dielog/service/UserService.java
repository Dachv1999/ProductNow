package com.loginDiego.Dielog.service;

import com.loginDiego.Dielog.models.User;
import com.loginDiego.Dielog.repository.UserRepository;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class UserService {

    //properties
    private final UserRepository userRepository;
    //


    public List<User> getAll(){

        return userRepository.getAll();
    }

    public Optional<User> getById(final Long userId){

        return userRepository.getById(userId);
    }

    public Optional<User>  create(final User user){

        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
        String hash = argon2.hash(1,1024,1,user.getPassword());

        user.setPassword(hash);
        return userRepository.create(user);
    }

    public boolean delete (final Long userId) {

        return userRepository.delete(userId) ? true : false;
    }
}
