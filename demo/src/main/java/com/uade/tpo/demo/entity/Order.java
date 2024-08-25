package com.uade.tpo.demo.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data; 


@Data
@Entity
@Table(name = "orders")
public class Order  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String orderDate;

    // Relación con la entidad `Client`
    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    // Relación con la entidad `Facture`
    @OneToOne(mappedBy = "order")
    private Facture facture;

    // Relación con la entidad `Detail`
    @OneToMany(mappedBy = "order")
    private List<Detail> details;
}
