package ca.springframework.spring6reactiveproject.repositories;

import ca.springframework.spring6reactiveproject.domain.Person;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class PersonRepositoryImpl  implements PersonRepository {

    Person michael = Person.builder().id(1L).firstName("Michael").lastName("Weston").build();
    Person fiona = Person.builder().id(2L).firstName("Fiona").lastName("Glenanne").build();
    Person sam = Person.builder().id(3L).firstName("Sam").lastName("Axe").build();
    Person jesse = Person.builder().id(4L).firstName("Jesse").lastName("Porter").build();

    @Override
    public Mono<Person> getById(Long id) {
        return Flux.just(michael, fiona, sam, jesse)
                .filter(person -> person.getId().equals(id))
                .next();
    }

    @Override
    public Flux<Person> findAll() {
        return Flux.just(michael, fiona, sam, jesse);
    }
}
