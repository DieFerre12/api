package com.uade.tpo.demo.service.user;

import com.uade.tpo.demo.entity.User;
import java.util.Optional;

public interface UserService {
    Optional<User> getUserById(Long id);
    Optional<User> getUserByEmail(String email);
    void deleteUser(Long id);
    User updateUser(Long id, User userDetails);
    User saveUser(String id,String email, String name, String password, String firstName);
}
