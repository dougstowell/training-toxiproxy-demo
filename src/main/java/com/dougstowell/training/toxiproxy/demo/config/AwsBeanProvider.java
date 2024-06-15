package com.dougstowell.training.toxiproxy.demo.config;

import java.net.URI;
import java.time.Duration;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.core.client.config.ClientOverrideConfiguration;

/**
 * Configuration class designed to create custom Spring Boot beans to use the AWS library.
 */
@Configuration
@AllArgsConstructor
public class AwsBeanProvider {
    @Bean
    public S3AsyncClient getAwsS3Client() {
        var cfg = ClientOverrideConfiguration.builder().apiCallTimeout(Duration.ofSeconds(10))
                .build();

        return S3AsyncClient.builder().region(Region.EU_WEST_2).forcePathStyle(true)
                .overrideConfiguration(cfg).endpointOverride(URI.create("http://localhost:4566"))
                .build();
    }
}
