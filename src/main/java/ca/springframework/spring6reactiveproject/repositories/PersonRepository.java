package ca.springframework.spring6reactiveproject.repositories;

import ca.springframework.spring6reactiveproject.domain.Person;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface PersonRepository {

    Mono<Person> getById(Long id);
    Flux<Person> findAll();
}
