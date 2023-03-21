package ca.springframework.spring6reactiveproject.controllers;


import ca.springframework.spring6reactiveproject.model.BeerDTO;
import ca.springframework.spring6reactiveproject.services.BeerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(BeerController.BEER_V1)
public class BeerController {

    public static final String BEER_V1 = "/api/beer";
    private final BeerService beerService;

    @GetMapping
    public Flux<BeerDTO> beerList(){
        return beerService.beerList();
    }

    @GetMapping("/{id}")
    public Mono<BeerDTO> getById(@PathVariable("id") Integer id){
        return beerService.getBeerById(id);
    }

    @PostMapping
    public Mono<ResponseEntity<Void>> saveBeer(@Validated @RequestBody BeerDTO beerDTO){
        return beerService.saveBeer(beerDTO)
                .map(savedDto ->
                    ResponseEntity.created(UriComponentsBuilder
                            .fromHttpUrl("http://localhost:9999/api/beer/" + savedDto.getId())
                            .build().toUri())
                            .build());
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<Void>> updateBeer(@PathVariable("id") Integer id,@Validated @RequestBody BeerDTO beerDTO){
        return beerService.updateBeer(id,beerDTO)
                .map(savedDto ->
                        ResponseEntity.noContent()
                                .build());
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteBeer(@PathVariable("id") Integer id){
        return beerService.deleteBeer(id)
                .map(response -> ResponseEntity.noContent().build()).then();
    }
}
