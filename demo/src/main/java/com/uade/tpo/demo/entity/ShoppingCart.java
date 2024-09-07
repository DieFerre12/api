package com.uade.tpo.demo.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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
public class ShoppingCart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany(mappedBy = "shoppingCarts", cascade = CascadeType.ALL)
    private List<Product> products;

    @Column
    private Double totalPrice;

    @ManyToOne
    @JoinColumn(name = "id_user", referencedColumnName = "id")
    private User user;


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProductsCart{
        private String id;
        private int cantidad;
        private double precio;
        public Object getQuantity() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'getQuantity'");
        }
    }
}

