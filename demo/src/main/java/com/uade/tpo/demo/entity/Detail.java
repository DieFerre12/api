package com.uade.tpo.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

// Entidad `Detail` para el manejo de detalles de una orden
@Data
@Entity
public class Detail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long id_order;
    @Column
    private Long id_product;
    @Column
    private Integer quantity;
    @Column
    private Double price;
    @Column
    private int amount;



    // Relación con la entidad `Order`
    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private Order order;

    // Relación con la entidad `Product`
    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;

    // Relación con la entidad `Facture`
    @ManyToOne
    @JoinColumn(name = "facture_id", referencedColumnName = "id")
    private Facture facture;
}
