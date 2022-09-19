package com.loginDiego.Dielog.controllers.impl;

import com.loginDiego.Dielog.controllers.UserController;
import com.loginDiego.Dielog.models.User;
import com.loginDiego.Dielog.service.UserService;
import com.loginDiego.Dielog.utils.JWTUtil;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserControllerImpl implements UserController {

    //properties
    private final UserService userService;
    private final JWTUtil jwtUtil;
    //

    @GetMapping
    @Override
    public ResponseEntity<List<User>> getAll(@RequestHeader(value = "Authorization") String token){
        if(!validateToken(token)){
            return new ResponseEntity<>(HttpStatus.NON_AUTHORITATIVE_INFORMATION);
        }
        return new ResponseEntity<>(userService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    @Override
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
        return userService.getById(userId)
                .map(user -> new ResponseEntity<>(user, HttpStatus.OK))
                .orElseThrow(() -> new EntityNotFoundException("El usuario con el id correspondiente no existe"));

    }

    @PostMapping
    @Override
    public ResponseEntity<User> create(@Valid @RequestBody User user){

        return userService.create(user).map(user1 -> new ResponseEntity<>(user1, HttpStatus.CREATED))
                .orElse(new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

    @DeleteMapping("/{userId}")
    @Override
    public ResponseEntity delete(@RequestHeader(value = "Authorization") String token, @NotNull @PathVariable("userId") final Long userId){

        if(!validateToken(token)){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        if((userService.getById(userId)) == null) {
            throw new EntityNotFoundException("No hay Un user con ese id");
        }else{
            if(userService.delete(userId)){
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
