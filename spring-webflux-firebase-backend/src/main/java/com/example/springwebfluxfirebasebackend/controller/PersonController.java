package com.example.springwebfluxfirebasebackend.controller;

import com.example.springwebfluxfirebasebackend.model.Person;
import com.example.springwebfluxfirebasebackend.repository.PersonRepository;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

@RestController
public class PersonController {

    private final PersonRepository repository;

    private final Sinks.Many<Person> sink;

    public PersonController(PersonRepository repository) {
        this.repository = repository;

        this.sink = Sinks.many().multicast().onBackpressureBuffer();
    }

    @GetMapping("/sse")   // 생략 가능 : "produces = MediaType.TEXT_EVENT_STREAM_VALUE"
    public Flux<ServerSentEvent<Person>> getAll() {
        return sink.asFlux().map(p -> ServerSentEvent.builder(p).build()).doOnCancel(() -> {
            sink.asFlux().blockLast();
        });
    }

    @PostMapping("/save")
    public Mono<Person> save(@RequestBody Person person) {
        return repository.save(person).doOnNext(p -> {
            sink.tryEmitNext(p);
        });
    }
}
