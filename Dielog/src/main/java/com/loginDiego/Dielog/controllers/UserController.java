package com.loginDiego.Dielog.controllers;

import com.loginDiego.Dielog.models.User;
import com.loginDiego.Dielog.repository.UserRepository;
import com.loginDiego.Dielog.utils.JWTUtil;
import com.sun.istack.NotNull;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JWTUtil jwtUtil;

    @GetMapping
    public ResponseEntity<List<User>> getAll(@RequestHeader(value = "Authorization") String token){
        if(!validateToken(token)){
            return new ResponseEntity<>(HttpStatus.NON_AUTHORITATIVE_INFORMATION);
        }
        return new ResponseEntity<>(userRepository.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getById(@RequestHeader(value = "Authorization") String token, @NotNull @PathVariable("userId") final Long userId){

        //User user = userRepository.getById(userId);
        //if(Objects.isNull(user)) {
        //    throw new EntityNotFoundException("No hay Un user con ese id");
        //}else{
        //    return user;
        //}
        if(!validateToken(token)){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return userRepository.getById(userId)
                .map(user -> new ResponseEntity<>(user, HttpStatus.OK))
                .orElseThrow(() -> new EntityNotFoundException("El usuario con el id correspondiente no existe"));

    }

    @PostMapping
    public ResponseEntity<User>  create(@Valid @RequestBody User user){

        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
        String hash = argon2.hash(1,1024,1,user.getPassword());

        user.setPassword(hash);
        return userRepository.create(user).map(user1 -> new ResponseEntity<>(user1, HttpStatus.CREATED))
                .orElse(new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity delete(@RequestHeader(value = "Authorization") String token, @NotNull @PathVariable("userId") final Long userId){

        if(!validateToken(token)){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        if((userRepository.getById(userId)) != null) {
            throw new EntityNotFoundException("No hay Un user con ese id");
        }else{
            if(userRepository.delete(userId)){
                return new ResponseEntity<>(HttpStatus.OK);
            }else {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
        }
    }

    private boolean validateToken(String token){
        String userId = jwtUtil.getKey(token);
        return userId != null;
    }
}
