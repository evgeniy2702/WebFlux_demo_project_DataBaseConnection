package com.example.WebFlux_demo_project_DataBaseConnection.controller;


import com.example.WebFlux_demo_project_DataBaseConnection.config.JwtUtil;
import com.example.WebFlux_demo_project_DataBaseConnection.domain.User;
import com.example.WebFlux_demo_project_DataBaseConnection.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@RestController
public class UserController {

    private final static ResponseEntity<Object> UNAUTHORIZED =
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final JwtUtil jwtUtil;

    public UserController(PasswordEncoder passwordEncoder, UserService userService, JwtUtil jwtUtil) {
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public Mono<ResponseEntity> login(ServerWebExchange swe) {
        return swe.getFormData().flatMap(credentials ->
                userService.findByUsername(credentials.getFirst("username"))
                        .cast(User.class)
                        .map(userDetails ->
                                passwordEncoder.matches(credentials.getFirst("password"),
                                        userDetails.getPassword()
                                )
                                        ? ResponseEntity.ok(jwtUtil.generateToken(userDetails))
                                        : UNAUTHORIZED
                        )
                        .defaultIfEmpty(UNAUTHORIZED)
        );
    }
}
