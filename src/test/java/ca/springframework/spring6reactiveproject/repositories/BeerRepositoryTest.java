package ca.springframework.spring6reactiveproject.repositories;

import ca.springframework.spring6reactiveproject.config.DatabaseConfig;
import ca.springframework.spring6reactiveproject.domain.Beer;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.context.annotation.Import;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;


@Slf4j
@DataR2dbcTest
@Import(DatabaseConfig.class) // DatabaseConfig class'ını import ediyoruz. Class r2dbc config yaparak TDD yapabilmemiz için. @CreatedDate, @LastModifiedDate gibi annotation'lar için.
class BeerRepositoryTest {


    @Autowired
    BeerRepository beerRepository;


    @Test
    void testCreateJson(){
        ObjectMapper objectMapper = new ObjectMapper();
        log.info(objectMapper.valueToTree(getTestBeer()).toString());
    }

    @Test
    void saveNewBeer() {
        beerRepository.save(getTestBeer())
                .subscribe(beer -> log.info(beer.toString()));
    }

    @Test
     Beer getTestBeer(){
        return Beer.builder()
                .beerName("Test Beer")
                .beerStyle("Test Style")
                .price(BigDecimal.TEN)
                .quantityOnHand(12)
                .upc("123456789012")
                .build();
    }
}