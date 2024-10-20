package com.uade.tpo.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Category {

    public Category() {
    }

    public Category(CategoryType categoryType) {
        this.categoryType = categoryType;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING) 
    @Column
    private CategoryType categoryType;

    public enum CategoryType {
        RUNNING,
        BASKETBALL,
        FOOTBALL,
        STREET,
        CASUAL
    }

}
