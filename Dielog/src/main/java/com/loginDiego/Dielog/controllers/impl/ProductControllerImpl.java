package com.loginDiego.Dielog.controllers.impl;

import com.loginDiego.Dielog.controllers.ProductController;
import com.loginDiego.Dielog.models.Product;
import com.loginDiego.Dielog.service.ProductService;
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
@RequestMapping("/products")
@AllArgsConstructor
public class ProductControllerImpl implements ProductController {

    //properties
    private final ProductService productService;
    private final JWTUtil jwtUtil;
    //

    @GetMapping
    @Override
    public ResponseEntity<List<Product>> getAll(@RequestHeader(value = "Authorization") String token){
        if(!validateToken(token)){
            return new ResponseEntity<>(HttpStatus.NON_AUTHORITATIVE_INFORMATION);
        }
        return new ResponseEntity<>(productService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{productId}")
    @Override
    public ResponseEntity<Product> getById(@RequestHeader(value = "Authorization") String token, @NotNull @PathVariable("productId") final Long productId){

        //User user = userRepository.getById(userId);
        //if(Objects.isNull(user)) {
        //    throw new EntityNotFoundException("No hay Un user con ese id");
        //}else{
        //    return user;
        //}
        if(!validateToken(token)){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return productService.getById(productId)
                .map(user -> new ResponseEntity<>(user, HttpStatus.OK))
                .orElseThrow(() -> new EntityNotFoundException("El usuario con el id correspondiente no existe"));

    }

    @PostMapping
    @Override
    public ResponseEntity<Product> create(@Valid @RequestBody Product product){

        return productService.create(product).map(user1 -> new ResponseEntity<>(user1, HttpStatus.CREATED))
                .orElse(new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

    @DeleteMapping("/{productId}")
    @Override
    public ResponseEntity delete(@RequestHeader(value = "Authorization") String token, @NotNull @PathVariable("productId") final Long productId){

        if(!validateToken(token)){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        if((productService.getById(productId)) == null) {
            throw new EntityNotFoundException("No hay un product con ese id");
        }else{
            if(productService.delete(productId)){
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
