package com.uade.tpo.demo.service.images;

import com.uade.tpo.demo.entity.Image;
import com.uade.tpo.demo.service.images.ImageService;

import org.springframework.stereotype.Service;

@Service
public interface ImageService {
    public Image create(Image image);

    public Image viewById(long id);

    public Image findByModel(String model);
}
