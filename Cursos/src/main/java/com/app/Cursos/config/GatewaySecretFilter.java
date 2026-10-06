package com.app.Cursos.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class GatewaySecretFilter extends OncePerRequestFilter {

    @Value("${gateway.internal-secret}")
    private String internalSecret;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        String uri = request.getRequestURI();

        if (uri.startsWith("/swagger-ui")
                || uri.startsWith("/v3/api-docs")
                || uri.startsWith("/h2-console")) {

            filterChain.doFilter(request, response);
            return;
        }

        String secret = request.getHeader("X-Gateway-Secret");

        if (secret == null || !secret.equals(internalSecret)) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().write("Acceso no autorizado");
            return;
        }

        filterChain.doFilter(request, response);
    }
}