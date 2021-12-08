package com.youssef.pharmacie.eventmicroservice.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Configuration
public class AwsConfig {

    private Environment env;

    @Autowired
    public AwsConfig(Environment env) {
        this.env = env;
    }

    @Bean
    public S3Client S3bucket(){
        return S3Client.builder().region(Region.US_EAST_1).build();
    }
}
