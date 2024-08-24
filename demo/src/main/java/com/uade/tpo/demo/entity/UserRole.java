package com.uade.tpo.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class UserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relación con la entidad `User`
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // Relación con la entidad `Role`
    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;
}