package com.dougstowell.training.toxiproxy.demo.controller;

import com.dougstowell.training.toxiproxy.demo.Constants;
import com.dougstowell.training.toxiproxy.demo.model.Event;
import com.dougstowell.training.toxiproxy.demo.service.DbService;
import com.dougstowell.training.toxiproxy.demo.service.S3Service;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Request;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Response;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

@AllArgsConstructor
@RestController
@RequestMapping(path = "api/v1/demo")
@Slf4j
public class DemoController {
    private final S3Service service;
    private final DbService dbService;

    @GetMapping("/")
    public String getVersion() {
        return "1.0";
    }

    @GetMapping("/cache")
    public ResponseEntity<String> getCacheEntries() {
        try {
            List<String> items = new ArrayList<>();
            dbService.findAll().forEach(e -> items.add(e.getId()));

            String msg =
                    String.format("%s items in the cache.... %s", items.size(), items.toString());
            return new ResponseEntity<>(msg, HttpStatus.OK);
        } catch (Exception ex) {
            String err = String.format("Error, could not get the cache contents at this time - %s",
                    ex.getMessage());
            return new ResponseEntity<>(err, HttpStatus.OK);
        }
    }

    @PostMapping("/cache")
    public ResponseEntity<String> createCache() {
        String key = UUID.randomUUID().toString();
        String body = String.format("Content of %s", key);

        try {
            Event event = new Event();
            event.setId(key);
            event.setBody(body);
            dbService.save(event);

            String msg = String.format("Cache entry created - %s", key);
            return new ResponseEntity<>(msg, HttpStatus.OK);
        } catch (Exception ex) {
            String err = String.format("Error, could not create a cache - %s", ex.getMessage());
            log.error(err);
            return new ResponseEntity<>(err, HttpStatus.OK);
        }
    }

    @DeleteMapping("/cache")
    public ResponseEntity<String> reset() {
        try {
            dbService.deleteAll();

            String msg = String.format("Items removed from the cache");
            return new ResponseEntity<>(msg, HttpStatus.OK);
        } catch (Exception ex) {
            String err =
                    String.format("Error, could remove items from the cache - %s", ex.getMessage());
            return new ResponseEntity<>(err, HttpStatus.OK);
        }
    }

    @Async
    @GetMapping("/file")
    public CompletableFuture<ResponseEntity<String>> getFileEntries() {
        ListObjectsV2Request request =
                ListObjectsV2Request.builder().bucket(Constants.IN_BUCKET).build();
        CompletableFuture<ListObjectsV2Response> future = service.list(request);

        return future.thenApply(response -> {
            List<String> items = new ArrayList<>();
            response.contents().forEach(e -> items.add(e.key()));

            String msg =
                    String.format("%s items in the file.... %s", items.size(), items.toString());
            return new ResponseEntity<>(msg, HttpStatus.OK);
        }).exceptionally(ex -> {
            String err = String.format("Error, could not get the file list at this time - %s",
                    ex.getMessage());
            return new ResponseEntity<>(err, HttpStatus.OK);
        });
    }

    @Async
    @PostMapping("/file")
    public CompletableFuture<ResponseEntity<String>> createFile() {
        String key = UUID.randomUUID().toString();
        String body = String.format("Content of %s", key);

        PutObjectRequest request =
                PutObjectRequest.builder().bucket(Constants.IN_BUCKET).key(key).build();
        CompletableFuture<PutObjectResponse> future = service.put(request, body);

        return future.thenApply(response -> {
            String msg = String.format("File entry created - %s", key);
            return new ResponseEntity<>(msg, HttpStatus.OK);
        }).exceptionally(ex -> {
            String err = String.format("Error, could not create a file - %s", ex.getMessage());
            log.error(err);
            return new ResponseEntity<>(err, HttpStatus.OK);
        });
    }
}
