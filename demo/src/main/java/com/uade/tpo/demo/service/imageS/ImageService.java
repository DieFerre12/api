package com.uade.tpo.demo.service.imageS;

import com.uade.tpo.demo.service.imageS.ImageService;
import com.uade.tpo.demo.entity.Image;

import org.springframework.stereotype.Service;

@Service
public interface ImageService {
    public Image create(Image image);

    public Image viewById(long id);

    public Image findByName(String name);
}
