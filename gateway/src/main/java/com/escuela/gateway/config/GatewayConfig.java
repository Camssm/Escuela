package com.escuela.gateway.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Autowired
    private AuthenticationFilter authenticationFilter;

    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder) {
    	
        var authFilter = authenticationFilter.apply(new AuthenticationFilter.Config());

        return builder.routes()
            .route(r -> r.path("/api/alumnos/**").filters(f -> f.filter(authFilter)).uri("lb://ms-alumnos"))
            .route(r -> r.path("/api/cursos/**").filters(f -> f.filter(authFilter)).uri("lb://ms-cursos"))
            .route(r -> r.path("/api/administracion/**").filters(f -> f.filter(authFilter)).uri("lb://ms-administracion"))
            .route(r -> r.path("/auth/**").uri("lb://security-service"))
            .build();
    }
}