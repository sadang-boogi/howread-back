package com.rebook.image.service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.rebook.image.domain.Directory;
import com.rebook.image.domain.ImageFile;
import com.rebook.image.exception.ImageException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

import static com.rebook.common.exception.ExceptionCode.INVALID_IMAGE;
import static com.rebook.common.exception.ExceptionCode.INVALID_IMAGE_PATH;


@Slf4j
@RequiredArgsConstructor
@Component
public class ImageUploader {

    private static final String CACHE_CONTROL_VALUE = "max-age=3153600";

    private final AmazonS3 s3Client;

    @Value("${cloud.aws.s3.bucketName}")
    private String bucket;

    @Value("${cloud.aws.s3.baseUrl}")
    private String baseUrl;



    public String uploadImage(final ImageFile imageFile, final Directory directory) {
        final String path = directory.getFullPath(imageFile.getHashedName());
        final ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(imageFile.getContentType());
        metadata.setContentLength(imageFile.getSize());
        metadata.setCacheControl(CACHE_CONTROL_VALUE);

        try (final InputStream inputStream = imageFile.getInputStream()) {
            s3Client.putObject(bucket, path, inputStream, metadata);
        } catch (final AmazonServiceException e) {
            throw new ImageException(INVALID_IMAGE_PATH);
        } catch (final IOException e) {
            throw new ImageException(INVALID_IMAGE);
        }
        return baseUrl + path;
    }


}
