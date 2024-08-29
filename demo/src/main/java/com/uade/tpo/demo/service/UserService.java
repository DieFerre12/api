package com.uade.tpo.demo.service;

import com.uade.tpo.demo.entity.User;
import java.util.Optional;

public interface UserService {
    User saveUser(User user);
    Optional<User> getUserById(Long id);
    Optional<User> getUserByEmail(String email);
    void deleteUser(Long id);
    User updateUser(Long id, User userDetails);
}
