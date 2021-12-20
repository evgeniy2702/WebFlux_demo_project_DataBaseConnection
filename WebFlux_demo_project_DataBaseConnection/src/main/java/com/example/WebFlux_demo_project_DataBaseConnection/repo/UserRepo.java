package com.example.WebFlux_demo_project_DataBaseConnection.repo;

import com.example.WebFlux_demo_project_DataBaseConnection.domain.User;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface UserRepo extends ReactiveCrudRepository<User, Long> {

    Mono<User> findByUsername(String name);
}
