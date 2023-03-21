package ca.springframework.spring6reactiveproject.services;

import ca.springframework.spring6reactiveproject.mappers.BeerMapper;
import ca.springframework.spring6reactiveproject.model.BeerDTO;
import ca.springframework.spring6reactiveproject.repositories.BeerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@RequiredArgsConstructor
@Service
public class BeerServiceImpl implements BeerService {

    private final BeerRepository beerRepository;
    private final BeerMapper beerMapper;
    @Override
    public Flux<BeerDTO> beerList() {
        return beerRepository.findAll()
                .map(beerMapper::beerToBeerDto);
    }

    @Override
    public Mono<BeerDTO> getBeerById(Integer id) {
        return beerRepository.findById(id)
                .map(beerMapper::beerToBeerDto);
    }

    @Override
    public Mono<BeerDTO> saveBeer(BeerDTO beerDTO) {
        return beerRepository.save(beerMapper.beerDtoToBeer(beerDTO))
                .map(beerMapper::beerToBeerDto);
    }

    @Override
    public Mono<BeerDTO> updateBeer(Integer id, BeerDTO beerDTO) {
        return beerRepository.findById(id)
                .map(beer -> {
                    beer.setBeerName(beerDTO.getBeerName());
                    beer.setBeerStyle(beerDTO.getBeerStyle());
                    beer.setPrice(beerDTO.getPrice());
                    beer.setUpc(beerDTO.getUpc());
                    return beer;
                })
                .flatMap(beerRepository::save)
                .map(beerMapper::beerToBeerDto);
    }
    @Override
    public Mono<Void> deleteBeer(Integer id) {
        return beerRepository.deleteById(id);
    }
}
