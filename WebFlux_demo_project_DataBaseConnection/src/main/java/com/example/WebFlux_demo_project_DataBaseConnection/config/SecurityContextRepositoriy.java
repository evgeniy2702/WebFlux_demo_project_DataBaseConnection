package com.example.WebFlux_demo_project_DataBaseConnection.config;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class SecurityContextRepositoriy implements ServerSecurityContextRepository {

    private final AuthenticationManager authenticationManager;

    public SecurityContextRepositoriy(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Mono<Void> save(ServerWebExchange exchange, SecurityContext context) {
        throw  new IllegalStateException("Save method not supported");
    }

    @Override
    public Mono<SecurityContext> load(ServerWebExchange exchange) {
        String authHeader = exchange.getRequest()
                .getHeaders()
                .getFirst(HttpHeaders.AUTHORIZATION);
        if(authHeader != null && authHeader.startsWith("Bearer ")){
            String authToken = authHeader.substring(7);

            UsernamePasswordAuthenticationToken auth =
                    new UsernamePasswordAuthenticationToken(authToken, authToken);
            return authenticationManager
                    .authenticate(auth)
                    .map(SecurityContextImpl::new);
        }
        return Mono.empty();
    }
}
