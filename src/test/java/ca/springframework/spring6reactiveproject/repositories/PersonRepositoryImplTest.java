package ca.springframework.spring6reactiveproject.repositories;

import ca.springframework.spring6reactiveproject.domain.Person;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.test.annotation.DirtiesContext;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@WebFluxTest(PersonRepositoryImpl.class)
class PersonRepositoryImplTest {

    PersonRepository personRepository;


    @BeforeEach
    void setUp() {
        personRepository = new PersonRepositoryImpl();
    }

    @Test
    void getById() {
        Mono<Person> personMono = personRepository.getById(1L);
        Person person = personMono.block(); // asenkron&non-blocking olduğu için block() ile bekliyoruz.
        log.info(person.toString());
    }

    @Test
    void findAll() {
        Flux<Person> personFlux = personRepository.findAll();
        personFlux.subscribe(person -> log.info(person.toString()));
    }

    @Test
    void testMap() {
        Flux<Person> personFlux = personRepository.findAll();
        personFlux.map(person -> person.getFirstName())
                .subscribe(firstName -> log.info(firstName));
    }

    @Test
    void testFluxBlockFirst() {
        Flux<Person> personFlux = personRepository.findAll();
        Person person = personFlux.blockFirst();
        log.info(person.toString());
    }

      @Test
        void testFluxMap() {
          Flux<Person> personFlux = personRepository.findAll();
          personFlux.map(Person::getFirstName) // Person class türünde getFirstname() methodu varsa bu şekilde kullanılabilir.
                  .subscribe(firstName -> log.info(firstName));      // Person::getFirstName == person -> person.getFirstName() -> for ile dönerek log yazdırma
      }

      @Test
        void testFluxToList() {
          Flux<Person> personFlux = personRepository.findAll();
          Mono<List<Person>> listMono = personFlux.collectList(); // Flux<Person> -> Mono<List<Person>>
          listMono.subscribe(people -> {
              //people -> mono listesi olduğu için tek kayıt dönecek. fakat içeriği list olduğu için for ile dönüyoruz.
              people.forEach(person -> log.info(person.toString()));
          });
       }

       @Test
       void testFilterOnName(){
              Flux<Person> personFlux = personRepository.findAll();
              personFlux.filter(person -> person.getFirstName().equals("Fiona"))
                     .subscribe(person -> log.info(person.toString()));
       }

       @Test
       void testFilterGetById(){
            Mono<Person> personMono = personRepository.findAll().next();
            personMono.subscribe(person -> log.info(person.toString()));
       }

       @Test
       //@DirtiesContext
       void testFilterGetByIdNotFound(){
        Flux<Person> personFlux = personRepository.findAll();
        final Long id = 10L;
        personFlux.filter(person -> person.getId().equals(id))
                .single()
                .doOnError(throwable -> {
                    log.info("Person not found with id: " + id);
                });
       }


    @Test
    void testGetByIdNotFoundStepVerifier() {
        Mono<Person> personMono = personRepository.getById(10L);
        StepVerifier.create(personMono)
                .expectNextCount(0);
        personMono.subscribe(person -> log.info(person.toString()));
    }
}