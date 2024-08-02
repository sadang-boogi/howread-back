package com.rebook.image.service;

import com.rebook.common.exception.ExceptionCode;
import com.rebook.image.domain.ImageEntity;
import com.rebook.image.domain.ImageFile;
import com.rebook.image.exception.ImageException;
import com.rebook.image.repository.ImageRepository;
import com.rebook.image.service.dto.ImageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
@Service
@RequiredArgsConstructor
public class ImageService {

    private final ImageUploader imageUploader;
    private final ImageRepository imageRepository;

    public ImageDto save(final MultipartFile image) {
        if (image.isEmpty()) {
            throw new ImageException(ExceptionCode.NULL_IMAGE);
        }

        //이미지 파일 저장
        ImageFile imageFile = new ImageFile(image);
        String imageUrl = imageUploader.uploadImage(imageFile);

        //이미지 엔티티 저장
        ImageEntity imageEntity = imageRepository.save(new ImageEntity(imageUrl));

        return ImageDto.from(imageEntity);
    }

}
