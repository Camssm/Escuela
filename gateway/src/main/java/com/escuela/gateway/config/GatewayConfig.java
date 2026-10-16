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

				.route("ms-cursos-docs", r -> r.path("/api/cursos/v3/api-docs")
						.filters(f -> f.rewritePath("/api/cursos/v3/api-docs", "/v3/api-docs")).uri("lb://MS-CURSOS"))
				.route("ms-alumnos-docs", r -> r.path("/api/alumnos/v3/api-docs")
						.filters(f -> f.rewritePath("/api/alumnos/v3/api-docs", "/v3/api-docs")).uri("lb://MS-ALUMNOS"))
				.route("ms-administracion-docs",
						r -> r.path("/api/administracion/v3/api-docs")
								.filters(f -> f.rewritePath("/api/administracion/v3/api-docs", "/v3/api-docs"))
								.uri("lb://MS-ADMINISTRACION"))
				.route("ms-security-docs", r -> r.path("/auth/v3/api-docs")
						.filters(f -> f.rewritePath("/auth/v3/api-docs", "/v3/api-docs")).uri("lb://SECURITY-SERVICE"))

				.route("ms-alumnos",
						r -> r.path("/api/alumnos/**").filters(f -> f.filter(authFilter)).uri("lb://MS-ALUMNOS"))
				.route("ms-cursos",
						r -> r.path("/api/cursos/**").filters(f -> f.filter(authFilter)).uri("lb://MS-CURSOS"))
				.route("ms-administracion",
						r -> r.path("/api/administracion/**").filters(f -> f.filter(authFilter))
								.uri("lb://MS-ADMINISTRACION"))
				.route("ms-security", r -> r.path("/auth/**").uri("lb://SECURITY-SERVICE"))
				.build();
	}
}
