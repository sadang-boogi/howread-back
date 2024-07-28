package com.rebook.image.controller.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ImageResponse {

    private String imagePath;

    public static ImageResponse of(final String imagePath) {
        return new ImageResponse(imagePath);
    }
}
