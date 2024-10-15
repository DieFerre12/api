package com.uade.tpo.demo.controllers.Imagen;

import org.springframework.web.bind.annotation.RestController;

import com.uade.tpo.demo.entity.Image;
import com.uade.tpo.demo.service.images.ImageService;

import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Base64;

import javax.sql.rowset.serial.SerialException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/images")
public class ImagesController {
    @Autowired
    private ImageService imageService;

    @CrossOrigin
    @GetMapping("/{id}")
    public ResponseEntity<ImageResponse> displayImage(@PathVariable("id") long id) throws IOException, SQLException {
        Image image = imageService.viewById(id);
        String encodedString = Base64.getEncoder()
                .encodeToString(image.getImage().getBytes(1, (int) image.getImage().length()));
        return ResponseEntity.ok().body(ImageResponse.builder().file(encodedString).id(id).build());
    }

    @GetMapping("/search/{name}")
    public ResponseEntity<ImageResponse> findImageByName(@PathVariable("name") String name) {
        Image image = imageService.findByName(name); // Asegúrate de que este método esté implementado
        if (image == null) {
            return ResponseEntity.notFound().build(); // Devuelve 404 si no se encuentra la imagen
        }
        // Utiliza el método para obtener la imagen en Base64
        String encodedImage = image.getImageAsBase64(); // Obtiene la imagen en formato Base64
        return ResponseEntity.ok().body(ImageResponse.builder().file(encodedImage).id(image.getId()).build());
    }

    @PostMapping("/add")
    public String addImagePost(AddFileRequest request) throws IOException, SerialException, SQLException {
        byte[] bytes = request.getFile().getBytes();
        Blob blob = new javax.sql.rowset.serial.SerialBlob(bytes);
        imageService.create(Image.builder().image(blob).build());
        return "created";
    }
}
