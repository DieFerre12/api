package com.uade.tpo.demo.entity;

import java.util.List;

import com.uade.tpo.demo.entity.Category.CategoryType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String description;

    @Column
    private String model;

    @Column
    private String genre;

    @Column
    private String image;

    @Enumerated(EnumType.STRING)
    private Brand brand;

    @Enumerated(EnumType.STRING)
    private Size size;

    @Column
    private Integer stock;

    @Column
    private Double price;

    @ManyToOne
    @JoinColumn(name = "categoryType", referencedColumnName = "id")
    private Category category;

    @ManyToMany
    @JoinTable(
        name = "product_shopping_cart",
        joinColumns = @JoinColumn(name = "product_id"),
        inverseJoinColumns = @JoinColumn(name = "shopping_cart_id")
    )

    private List<ShoppingCart> shoppingCarts;

}
