package ca.springframework.spring6reactiveproject.mappers;

import ca.springframework.spring6reactiveproject.domain.Beer;
import ca.springframework.spring6reactiveproject.model.BeerDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BeerMapper {
    Beer beerDtoToBeer(BeerDTO dto);

    BeerDTO beerToBeerDto(Beer beer);
}