package com.dougstowell.training.toxiproxy.demo.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;

@ConfigurationProperties(prefix = "redis.settings")
@AllArgsConstructor
@Getter
public class RedisConfiguration {
    private final String host;
    private final int port;
}
