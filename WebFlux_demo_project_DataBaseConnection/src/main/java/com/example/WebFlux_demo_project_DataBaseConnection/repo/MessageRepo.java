package com.example.WebFlux_demo_project_DataBaseConnection.repo;

import com.example.WebFlux_demo_project_DataBaseConnection.domain.Message;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface MessageRepo extends ReactiveCrudRepository<Message, Long> {
    
}
