package com.uade.tpo.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.uade.tpo.demo.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    public Optional<User> findByEmail(String email);

    public Optional<User> findById(Long id);  

    public void deleteById(Long id);          
}
