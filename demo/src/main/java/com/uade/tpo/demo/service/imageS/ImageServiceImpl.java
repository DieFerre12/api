package com.uade.tpo.demo.service.images;

import com.uade.tpo.demo.entity.Image;
import com.uade.tpo.demo.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ImageServiceImpl implements ImageService {
    @Autowired
    private ImageRepository imageRepository;

    @Override
    public Image create(Image image) {
        return imageRepository.save(image);
    }

    @Override
    public Image viewById(long id) {
        return imageRepository.findById(id).orElse(null);
    }
    public Image findByName(String name) {
        return imageRepository.findByName(name);  // Implementación correcta del método
    }
}
