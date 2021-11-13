package com.youssef.pharmacie.apigateway;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiGatewayConfig {
    @Bean
    public RouteLocator gatewayRouter(RouteLocatorBuilder builder){
        return builder.routes()
                .route(predicateSpec -> predicateSpec.path("/api/users/**").uri("lb://user-microservice"))
                .route(predicateSpec -> predicateSpec.path("/api/events/**").uri("lb://event-microservice"))
                .build();
    }
}
