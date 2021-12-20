package com.example.WebFlux_demo_project_DataBaseConnection.handlers;

import com.example.WebFlux_demo_project_DataBaseConnection.domain.Message;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

@Component
public class GreetingHandler {

    public Mono<ServerResponse> hello(ServerRequest request) {

//        BodyInserter<String, ReactiveHttpOutputMessage> body =
//                BodyInserters.fromValue("Hello, Spring!");

        Long start = request.queryParam("start")
                .map(Long::valueOf)
                .orElse(0L);

        Long count = request.queryParam("count")
                .map(Long::valueOf)
                .orElse(3L);

        Flux<Message> data = Flux
                .just(
                        "Hello. reactive!",
                        "More then one",
                        "Third post",
                        "Fourth post",
                        "Fifth post"
                )
                .skip(start)
                .take(count)
                .map(Message::new);

        return ServerResponse.
                ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(data, Message.class);
    }

    public Mono<ServerResponse> user(ServerRequest serverRequest){

            String user = serverRequest.queryParam("user")
                    .orElse("Nobody");
            return ServerResponse
                    .ok()
                    .render("index", Map.of("user" ,user));
        }

}
