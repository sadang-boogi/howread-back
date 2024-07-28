package com.rebook.image.service;

import com.rebook.image.controller.response.ImageResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface ImageService {
    ImageResponse save(MultipartFile image);
}
