package com.rebook.image.reponse;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ImagesResponse {

    private String imageUrl;

    public static ImagesResponse of(final String imageUrl) {
        return new ImagesResponse(imageUrl);
    }
}
