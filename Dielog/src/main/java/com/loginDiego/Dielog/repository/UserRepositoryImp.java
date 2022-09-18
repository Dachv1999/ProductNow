package com.loginDiego.Dielog.repository;

import com.loginDiego.Dielog.models.User;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class UserRepositoryImp implements UserRepository{

    //Se tiene que importar
    @PersistenceContext
    EntityManager em;

    @Override
    public List<User> getAll() {
        String query = "SELECT u FROM User u";
        return em.createQuery(query)
                .getResultList();
    }

    @Override
    public Optional<User> getById(Long userId) {
        String query = "SELECT u FROM User u WHERE u.id = :id";
        return em.createQuery(query, User.class)
                .setParameter("id",userId)
                .getResultList()
                .stream().findFirst();
    }

    @Override
    public Optional<User> create(User user) {
        return Optional.of(em.merge(user));
    }

    @Override
    public boolean delete(Long userId) {
        User userDelete = em.find(User.class, userId);
        em.remove(userDelete);
        return true;
    }

    @Override
    public User getValidUser(User user) {
        String query = "SELECT u FROM User u WHERE u.email = :email";
        List<User> userList =  em.createQuery(query)
                    .setParameter("email", user.getEmail())
                    .getResultList();

        if(userList.isEmpty()){
            return null;
        }

        String passwordComparar = userList.get(0).getPassword();

        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);

        if(argon2.verify(passwordComparar, user.getPassword())){
            return userList.get(0);
        }else{
            return null;
        }
    }
}
