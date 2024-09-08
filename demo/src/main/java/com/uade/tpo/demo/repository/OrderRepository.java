package com.uade.tpo.demo.repository;


import com.uade.tpo.demo.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    // Puedes agregar métodos de consulta personalizados aquí si es necesario
}