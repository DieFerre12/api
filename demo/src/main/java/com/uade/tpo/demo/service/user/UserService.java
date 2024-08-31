package com.uade.tpo.demo.service.user;

import com.uade.tpo.demo.entity.User;
import java.util.Optional;

public interface UserService {
    public Optional<User> getUserById(Long id);
    
    public Optional<User> getUserByEmail(String email);
    
    public void deleteUser(Long id);
    
    public User updateUser(Long id, User userDetails);
    
    public User saveUser(String id,String email, String name, String password, String firstName);
}
