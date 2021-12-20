package com.example.WebFlux_demo_project_DataBaseConnection.service;

import com.example.WebFlux_demo_project_DataBaseConnection.domain.Message;
import com.example.WebFlux_demo_project_DataBaseConnection.repo.MessageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class MessageService {

    private final MessageRepo messageRepo;

    @Autowired
    public MessageService(MessageRepo messageRepo) {
        this.messageRepo = messageRepo;
    }

    public Flux<Message> list(){
        return messageRepo.findAll();
    }

    public Mono<Message> addOne(Message message){
        return messageRepo.save((message));
    }
}
