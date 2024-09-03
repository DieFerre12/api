package com.uade.tpo.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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

    @Enumerated(EnumType.STRING)  // Guarda el nombre del enum como un String en la base de datos
    @Column
    private CategoryType categoryType;

    @OneToMany (mappedBy = "category")
    private Product product;

    public enum CategoryType {
        RUNNING,
        BASKETBALL,
        FOOTBALL,
        STREET,
        CASUAL
    }
}
