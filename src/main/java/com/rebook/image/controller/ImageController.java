package com.rebook.image.controller;

import com.rebook.image.controller.response.ImageResponse;
import com.rebook.image.service.ImageService;
import com.rebook.image.service.dto.ImageDto;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
        final ImageDto imageDto = imageService.save(image);
        ImageResponse response = ImageResponse.from(imageDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}
