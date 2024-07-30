package com.rebook.image.service;

import com.rebook.image.controller.response.ImageResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImageService {

    public ImageResponse save(MultipartFile image) {
        // 로직
        return new ImageResponse();
    }

}
