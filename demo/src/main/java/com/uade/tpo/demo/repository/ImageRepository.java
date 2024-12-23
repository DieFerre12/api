package com.uade.tpo.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.uade.tpo.demo.entity.Image;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
    Image findByModel(String model); 
}


