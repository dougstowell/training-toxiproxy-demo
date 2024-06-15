package com.dougstowell.training.toxiproxy.demo.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;

@ConfigurationProperties(prefix = "aws.settings")
@AllArgsConstructor
@Getter
public class AwsConfiguration {
    private final String accessKeyId;
    private final String secretAccessKey;
    private final String endpointOverride;
}
