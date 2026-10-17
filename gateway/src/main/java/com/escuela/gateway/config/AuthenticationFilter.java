package com.escuela.gateway.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import io.jsonwebtoken.Claims;
import reactor.core.publisher.Mono;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    @Autowired
    private RouteValidator validator;

    @Autowired
    private JwtUtil jwtUtil;

    public AuthenticationFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {

            ServerHttpRequest request = exchange.getRequest();

            if (HttpMethod.OPTIONS.equals(request.getMethod()) || !validator.isSecured.test(request)) {
                return chain.filter(exchange);
            }

            String authHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return responder(exchange, HttpStatus.UNAUTHORIZED);
            }

            Claims claims;
            try {
                claims = jwtUtil.getClaims(authHeader.substring(7));
            } catch (Exception e) {
                return responder(exchange, HttpStatus.UNAUTHORIZED);
            }

            String rol = claims.get("rol", String.class);
            String username = claims.getSubject();

            if (rol == null || !permitido(rol, request.getMethod(), request.getURI().getPath())) {
                return responder(exchange, HttpStatus.FORBIDDEN);
            }

            ServerHttpRequest nueva = request.mutate().headers(h -> {
                h.remove("X-User-Rol");
                h.remove("X-User-Username");
                h.remove("X-Gateway-Secret");
                h.add("X-User-Rol", rol);
                h.add("X-User-Username", username);
                h.add("X-Gateway-Secret", "ApoloRuben2026");
            }).build();

            return chain.filter(exchange.mutate().request(nueva).build());
        };
    }

    private boolean permitido(String rol, HttpMethod method, String path) {
        boolean lectura = HttpMethod.GET.equals(method);

        if (path.startsWith("/api/alumnos")) {
            return lectura ? List.of("PRECEPTOR", "DIRECTOR", "DOCENTE", "ADMINISTRATIVO").contains(rol)
                    : rol.equals("PRECEPTOR");
        }
        if (path.startsWith("/api/cursos")) {
            return lectura ? true : List.of("ROOT", "PRECEPTOR").contains(rol);
        }
        if (path.startsWith("/api/evaluaciones") || path.startsWith("/api/notas")) {
            String rolLimpio = (rol != null) ? rol.replace("ROLE_", "").trim().toUpperCase() : "";
            return lectura ? List.of("DOCENTE", "DIRECTOR", "ROOT").contains(rolLimpio) 
                           : List.of("DOCENTE", "ROOT").contains(rolLimpio);
        }
        if (path.startsWith("/api/administracion")) {
            return lectura ? List.of("ROOT", "ADMINISTRATIVO", "DIRECTOR", "PRECEPTOR").contains(rol)
                    : List.of("ROOT", "ADMINISTRATIVO").contains(rol);
        }
        return false;
    }

    private Mono<Void> responder(ServerWebExchange exchange, HttpStatus status) {
        exchange.getResponse().setStatusCode(status);
        return exchange.getResponse().setComplete();
    }

    public static class Config {
    }
}