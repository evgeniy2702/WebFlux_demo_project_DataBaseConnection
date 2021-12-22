package com.example.WebFlux_demo_project_DataBaseConnection.config;


import com.example.WebFlux_demo_project_DataBaseConnection.handlers.GreetingHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.*;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;

@Configuration(proxyBeanMethods = false)
public class GreetingRouter {

    @Bean
    public RouterFunction<ServerResponse> route(GreetingHandler greetingHandler) {

        RequestPredicate route_hello = GET("/hello")
                .and(RequestPredicates.accept(MediaType.TEXT_PLAIN));

        return RouterFunctions

                .route(route_hello, greetingHandler::hello)
                .andRoute(
                        GET("/main"),
                        ServerRequest -> {
                            return ServerResponse
                                    .ok()
                                    .contentType(MediaType.TEXT_PLAIN)
                                    .body(BodyInserters.fromValue("Main page"));
                        }
                )
                .andRoute(
                        GET("/user"), greetingHandler::user)
                .andRoute(
                        GET("/"), greetingHandler::user );
    }
}