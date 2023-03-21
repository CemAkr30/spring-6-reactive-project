package ca.springframework.spring6reactiveproject.services;

import ca.springframework.spring6reactiveproject.model.BeerDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BeerService {
    Flux<BeerDTO> beerList();

    Mono<BeerDTO> getBeerById(Integer id);

    Mono<BeerDTO> saveBeer(BeerDTO beerDTO);

    Mono<BeerDTO> updateBeer(Integer id, BeerDTO beerDTO);

    Mono<Void> deleteBeer(Integer id);
}
