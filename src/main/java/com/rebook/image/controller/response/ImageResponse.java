package com.rebook.image.controller.response;

import com.rebook.image.domain.ImageEntity;
import com.rebook.image.service.dto.ImageDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ImageResponse {

    private Long id;
    public static ImageResponse from(final ImageDto dto) {

        return new ImageResponse(dto.getId());
    }

}
