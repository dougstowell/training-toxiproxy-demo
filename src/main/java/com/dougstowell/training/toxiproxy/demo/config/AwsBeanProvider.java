package com.dougstowell.training.toxiproxy.demo.config;

import java.net.URI;
import java.time.Duration;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.client.config.ClientOverrideConfiguration;

@Configuration
@AllArgsConstructor
public class AwsBeanProvider {
    private final AwsConfiguration config;

    @Bean
    public S3AsyncClient getAwsS3Client() {
        var cfg = ClientOverrideConfiguration.builder().apiCallTimeout(Duration.ofSeconds(10))
                .build();
        var cred = AwsBasicCredentials.builder().accessKeyId(config.getAccessKeyId())
                .secretAccessKey(config.getSecretAccessKey()).build();

        return S3AsyncClient.builder().credentialsProvider(StaticCredentialsProvider.create(cred))
                .region(Region.EU_WEST_2).forcePathStyle(true).overrideConfiguration(cfg)
                .endpointOverride(URI.create(config.getEndpointOverride())).build();
    }
}
