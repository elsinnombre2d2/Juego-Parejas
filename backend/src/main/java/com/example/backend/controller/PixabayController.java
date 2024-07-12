package com.example.backend.controller;

import com.example.backend.model.PixabayImage;
import com.example.backend.service.PixabayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/images")
@CrossOrigin(origins = "*")
public class PixabayController {

    @Autowired
    private PixabayService pixabayService;

    @GetMapping("/reverse")
    public PixabayImage getReverseImage() {
        return pixabayService.getReverseImageUrl();
    }
    @GetMapping("/anverse")
    public List<PixabayImage> getAnverseImages() {
        return pixabayService.getAnverseImagesUrl();
    }
}