package com.example.WebFlux_demo_project_DataBaseConnection.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Mono;

@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class WebSecurityConfig {

    private final AuthenticationManager authenticationManager;
    private final SecurityContextRepositoriy securityContextRepositoriy;

    public WebSecurityConfig(AuthenticationManager authenticationManager, SecurityContextRepositoriy securityContextRepositoriy) {
        this.authenticationManager = authenticationManager;
        this.securityContextRepositoriy = securityContextRepositoriy;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity httpSecurity){
        return httpSecurity
                .exceptionHandling()
                .authenticationEntryPoint(
                        (swe, e) ->
                                Mono.fromRunnable(
                                        () -> swe.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED)
                                )
                )
                .accessDeniedHandler(
                        (swe, e) ->
                                Mono.fromRunnable(
                                        () -> swe.getResponse().setStatusCode(HttpStatus.FORBIDDEN)
                                )
                )
                .and()
                .csrf().disable()
                .formLogin().disable()
                .httpBasic().disable()
                .authenticationManager(authenticationManager)
                .securityContextRepository(securityContextRepositoriy)
                .authorizeExchange()
                .pathMatchers("/", "/login", "/favicon.ico").permitAll()
                .pathMatchers("/controller").hasRole("ADMIN")
                .anyExchange().authenticated()
                .and()
                .build();
    }
}
