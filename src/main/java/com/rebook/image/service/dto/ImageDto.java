package com.rebook.image.service.dto;

import com.rebook.image.domain.ImageEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ImageDto {
    private Long id;

    public static ImageDto from(ImageEntity imageEntity) {
        return new ImageDto(imageEntity.getId());
    }
}
