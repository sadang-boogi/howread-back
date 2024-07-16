package com.rebook.image.controller;

import com.rebook.image.FilesParameter;
import com.rebook.image.domain.Directory;
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

    @Operation(summary = "Upload an book image")
    @PostMapping(value = "/upload/book-image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ImagesResponse> uploadBookImage(@FilesParameter @RequestPart final MultipartFile image) {
        final ImagesResponse imagesResponse = imageService.save(image, new Directory("image", "book"));
        return ResponseEntity.created(URI.create(imagesResponse.getImagePath())).body(imagesResponse);
    }

    @Operation(summary = "Upload an user image")
    @PostMapping(value = "/upload/user-image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ImagesResponse> uploadUserImage(@FilesParameter @RequestPart final MultipartFile image) {
        final ImagesResponse imagesResponse = imageService.save(image, new Directory("image", "user"));
        return ResponseEntity.created(URI.create(imagesResponse.getImagePath())).body(imagesResponse);
    }

}
