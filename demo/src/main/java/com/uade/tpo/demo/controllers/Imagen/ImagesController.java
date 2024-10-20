package com.uade.tpo.demo.controllers.Imagen;

import org.springframework.web.bind.annotation.RestController;

import com.uade.tpo.demo.entity.Image;
import com.uade.tpo.demo.service.images.ImageService;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;

import javax.sql.rowset.serial.SerialBlob;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;  // Cambié a org.springframework.http.HttpHeaders
import org.springframework.http.MediaType;   // Import correcto para MediaType
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@CrossOrigin(origins = "http://localhost:5173") 
@RestController
@RequestMapping("/images")
public class ImagesController {

    @Autowired
    private ImageService imageService;
    @CrossOrigin
    @GetMapping("/{id}")
    public ResponseEntity<InputStreamResource> getImage(@PathVariable("id") long id) {
        Image image = imageService.viewById(id); 

        if (image != null && image.getImage() != null) {
            try {
                InputStream imageStream = image.getImage().getBinaryStream(); 
                InputStreamResource resource = new InputStreamResource(imageStream);
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + image.getModel() + "\"")
                        .contentType(MediaType.IMAGE_JPEG)  // Cambia esto según el tipo de imagen (JPEG, PNG, etc.)
                        .body(resource);
            } catch (SQLException e) {
                e.printStackTrace(); 
            }
        }
        return ResponseEntity.notFound().build(); 
    }

    @GetMapping("/search/{model}")
    public ResponseEntity<InputStreamResource> findImageByName(@PathVariable("model") String model) {
        Image image = imageService.findByModel(model);
        
        if (image != null && image.getImage() != null) {
            try {
                InputStream imageStream = image.getImage().getBinaryStream(); 
                InputStreamResource resource = new InputStreamResource(imageStream);
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + image.getModel() + "\"")
                        .contentType(MediaType.IMAGE_JPEG) 
                        .body(resource);
            } catch (SQLException e) {
                e.printStackTrace(); 
            }
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/add/{model}")    
    public String addImagePost(AddFileRequest request, @PathVariable ("model") String model) throws IOException, SQLException {        
        byte[] bytes = request.getFile().getBytes();          
        Blob blob = new SerialBlob(bytes);                    
        imageService.create(Image.builder()                
            .image(blob)                                                          
            .model(model)               
            .build());        
        return "Imagen creada correctamente para el modelo: " + model; // Incluimos el modelo en la respuesta
    }
    
}