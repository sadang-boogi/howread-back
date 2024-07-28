package com.rebook.image.controller;

import com.rebook.image.controller.response.ImageResponse;
import com.rebook.image.service.ImageService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;


@RequiredArgsConstructor
@RequestMapping("/api/v1/image")
@RestController
public class ImageController {

    private final ImageService imageService;

    @Operation(summary = "Upload an image")
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ImageResponse> uploadBookImage(@RequestPart final MultipartFile image) {
        final ImageResponse imageResponse = imageService.save(image);
        return ResponseEntity.created(URI.create("/api/v1/image/" + imageResponse.getId())).body(imageResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ImageResponse> getImageById(@PathVariable Long id) {
        final ImageResponse imageResponse = imageService.getImage(id);
        return ResponseEntity.ok().body(imageResponse);
    }

}
