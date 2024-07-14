package com.rebook.image.controller;

import com.rebook.image.FilesParameter;
import com.rebook.image.reponse.ImagesResponse;
import com.rebook.image.service.ImageService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;

@RequiredArgsConstructor
@RequestMapping("/api/v1")
@RestController
public class ImageController {

    private final ImageService imageService;

    @Operation(summary = "Upload an image")
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ImagesResponse> uploadImage(@FilesParameter @RequestPart final MultipartFile image) {
        final ImagesResponse imagesResponse = imageService.save(image);
        return ResponseEntity.created(URI.create(imagesResponse.getImageName())).body(imagesResponse);
    }

}
