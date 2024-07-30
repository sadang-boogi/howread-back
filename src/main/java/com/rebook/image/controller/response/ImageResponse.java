package com.rebook.image.controller.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ImageResponse {

    private Long id;
    private String url;

    public static ImageResponse of(final Long id, final String url) {
        return new ImageResponse(id, url);
    }

}
