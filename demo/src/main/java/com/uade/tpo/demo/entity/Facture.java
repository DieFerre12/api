package com.uade.tpo.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Data
@Entity
public class Facture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private long id_client;

    @Column
    private String factureDate;

    @Column
    private int num_payment;

    @Column
    private String detail;

    @Column
    private int discount;

    @Column
    private int total;

    @Column
    private String payment;

    // Relación con la entidad `Order`
    @OneToOne
    @JoinColumn(name = "order_id")
    private Order order;

    // Relación con la entidad `MetodoPago`
    @OneToOne
    @JoinColumn(name = "metodo_pago_id", referencedColumnName = "id")
    private MetodoPago metodoPago;

    // Relación con la entidad `Client`
    @ManyToOne
    @JoinColumn(name = "client_id") // Este es el nombre de la columna en la tabla `Facture`
    private User client;
}
