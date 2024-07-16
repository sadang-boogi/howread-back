package com.rebook.image.service;

import com.rebook.common.exception.ExceptionCode;
import com.rebook.image.domain.Directory;
import com.rebook.image.domain.ImageFile;
import com.rebook.image.exception.ImageException;
import com.rebook.image.reponse.ImagesResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final ImageUploader imageUploader;

    public ImagesResponse save(final MultipartFile image, final Directory directory) {
        if (image.isEmpty()) {
            throw new ImageException(ExceptionCode.NULL_IMAGE);
        }

        ImageFile imageFile = new ImageFile(image);
        String imagePath = imageUploader.uploadImage(imageFile, directory);

        return ImagesResponse.of(imagePath);
    }

}
