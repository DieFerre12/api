package com.uade.tpo.demo.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.InputStream;
import java.sql.Blob;
import java.util.Base64;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "image_table")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Blob image;

    @Column(unique = true, nullable = false) 
    private String model; 

    private String name;

    @JsonProperty("image")
    public String getImageAsBase64() {
        if (image != null) {
            try (InputStream binaryStream = image.getBinaryStream()) {
                byte[] bytes = binaryStream.readAllBytes();
                return Base64.getEncoder().encodeToString(bytes);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
