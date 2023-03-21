package ca.springframework.spring6reactiveproject.controllers;

import ca.springframework.spring6reactiveproject.domain.Beer;
import ca.springframework.spring6reactiveproject.model.BeerDTO;
import ca.springframework.spring6reactiveproject.services.BeerService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

/**
 * WebClient, HTTP isteklerini gerçekleştirmek için non-blocking ve asenkron
 * işlemleri destekleyen reactive bir HTTP client’dır. Reactive işlemlerin yanında senkron
 * ve blocking istekleri de destekler. Spring WebFlux ile birlikte gelen
 * WebClient uçtan uca tüm isteklerimizi gerçekleştirebileceğimiz senkron/asenkron
 * yapıya sahip bir HTTP client yapısı sunar.
 * */

/**
 * WebClient RestTemplate’e alternatif olarak geliştirilen senkron, asenkron ve non-blocking operasyonları destekler
 * ve streaming özellikleri barındırır, modern ve yenilikçi metotlar sunarak reactive programlamayı destekler.
 *
 * Tanımlamalarımızdan da yola çıkarak ikisinin arasında büyük farklar olduğunu hızlıca görebilsek de bir özet geçmekte
 * fayda var. Özellikle rest template’in bakım moduna alınacağını duyduktan sonra alternatiflerine bakmakta fayda var.
 * WebClient en güçlü alternatif olarak karşımıza çıkıyor.
 *
 * Webclient vs RestTemplate
 * Her istek için RestTemplate yeni bir Thread oluşturur ve bu isteğe yanıt gelene kadar kullanır.
 * Bir istek gönderdikten sonra RestTemplate, daha önce tanımlanmış bir timout’a ulaşılana kadar response bekler.
 * Bu işlem Thread’i bloklar. Uygulamada çok sayıda istek atılıyor ise bununla orantılı olarak çok sayıda
 * thread kullanılacak ve connection oluşacaktır.
 * Bu da maliyet ve server’a yük olarak geri dönecektir ve bu sayıların artması performansta
 * problemlere neden olabilir.
 *
 * RestTemplate’in aksine, WebClient asenkron ve non-blockingdir. Spring WebFlux’ın desteklediği gibi
 * reactive programlamayı destekler ve event-driven mimarıyı örnek alır. WebClient ile bir istek atıldığında
 * isteği atan thread bloklanmadan hayatına devam eder, dolayısıyla blocking bir yapıda çalışmaz, böylece asenkron
 * bir yapı sunar. Önemli bir not ise WebClient block() metoduyla birlikte RestTemplate benzeri senkron işlemleri de desteklemektedir.
**/
@TestMethodOrder(MethodOrderer.OrderAnnotation.class) // order sıralar methodları alfabetik olarak
@SpringBootTest
@AutoConfigureWebTestClient
class BeerControllerTest {

    @Autowired
    WebTestClient webTestClient;

    BeerService beerService;

    @Test
    void testListBeers(){
        webTestClient.get().uri("/api/beer")
                .exchange()
                .expectHeader().valueEquals("Content-Type", "application/json")
             //   .expectBody().jsonPath("$[0].beerName").isEqualTo("Mango Bobs")
                .expectBody().jsonPath("$.size()").isEqualTo(3);
    }

    @Test
    void testGetById(){
        webTestClient.get().uri("/api/beer/1")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().valueEquals("Content-Type", "application/json")
                .expectBody(BeerDTO.class);
        // expect-> dönüş değerlerini test eder
    }

    @Test
    void testSaveNewBeer(){
        webTestClient.post().uri("/api/beer")
                .body(Mono.just(getTestBeer()), BeerDTO.class)
                .header("Content-Type", "application/json")
                .exchange()
                .expectStatus().isCreated();
    }

    Beer getTestBeer(){
        return Beer.builder()
                .beerName("Test Beer")
                .beerStyle("Test Style")
                .price(BigDecimal.TEN)
                .quantityOnHand(12)
                .upc("123456789012")
                .build();
    }


    @Test
    void testUpdateBeer(){
        getTestBeer().setBeerName("Deniz TEST");
        webTestClient.put().uri("/api/beer/1")
                .body(Mono.just(getTestBeer()), BeerDTO.class)
                .header("Content-Type", "application/json")
                .exchange()
                .expectStatus().isNoContent();
    }

    /**
     * Bu listenin siralamsini class isimlerine harflerine göre siralar. “@Order” annotation
     * tam olarak burada isimizi görür. Eger implemente edilmis
     * siniflarin siralamasini springin ön gördügü degil de bizim istedigimiz bir sirada
     * olmasini istiyorsak bu annotationu kullanabiliriz.*/
    @Test
    //@Order(1)
    void testDeleteBeer(){
        webTestClient.delete().uri("/api/beer/1")
                .exchange()
                .expectStatus().isOk();
    }

}