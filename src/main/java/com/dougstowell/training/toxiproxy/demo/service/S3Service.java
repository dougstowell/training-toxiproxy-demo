package com.dougstowell.training.toxiproxy.demo.service;

import java.util.concurrent.CompletableFuture;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.async.AsyncRequestBody;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Request;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Response;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

@Service
@AllArgsConstructor
public class S3Service {
    private final S3AsyncClient client;

    public CompletableFuture<ListObjectsV2Response> list(ListObjectsV2Request request) {
        return client.listObjectsV2(request);
    }

    public CompletableFuture<PutObjectResponse> put(PutObjectRequest request, String item) {
        return client.putObject(request, AsyncRequestBody.fromString(item));
    }
}
