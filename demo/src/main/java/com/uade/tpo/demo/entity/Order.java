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

    @Column
    private String paymentMethod; // Agregamos el campo del método de pago

    // Relación con la entidad `Client`
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    // Relación con la entidad `Facture`
    @OneToOne(mappedBy = "order")
    private Facture facture;

    // Relación con la entidad `Detail`
    @OneToMany(mappedBy = "order")
    private List<Detail> details;

    // Método para calcular el total con descuento o recargo
    public double calculateTotal(double baseAmount) {
        double total = baseAmount;
        switch (paymentMethod.toLowerCase()) {
            case "tarjeta":
                total += baseAmount * 0.10; // 10% de recargo
                break;
            case "efectivo":
                total -= baseAmount * 0.15; // 15% de descuento
                break;
            case "mercado_pago":
                // No hay cambio en el total
                break;
            default:
                throw new IllegalArgumentException("Método de pago no válido");
        }
        return total;
    }
}
