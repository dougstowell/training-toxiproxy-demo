package com.dougstowell.training.toxiproxy.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import com.dougstowell.training.toxiproxy.demo.config.AwsConfiguration;

@SpringBootApplication
@EnableConfigurationProperties({AwsConfiguration.class})
public class DemoApplication {
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}
