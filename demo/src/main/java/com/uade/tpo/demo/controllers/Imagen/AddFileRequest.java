package com.uade.tpo.demo.controllers.Imagen;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AddFileRequest {
    private String model;
    private MultipartFile file;
}
