package ca.springframework.spring6reactiveproject.repositories;

import ca.springframework.spring6reactiveproject.domain.Beer;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BeerRepository extends ReactiveCrudRepository<Beer, Integer> {
}
