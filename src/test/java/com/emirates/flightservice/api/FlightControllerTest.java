package com.emirates.flightservice.api;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.emirates.flightservice.model.Flight;
import com.emirates.flightservice.service.FlightService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@RunWith(SpringRunner.class)
@WebFluxTest(FlightController.class)
public class FlightControllerTest {

  @Autowired private WebTestClient webTestClient;

  @MockBean private FlightService flightService;

  @Test
  public void getFlightsTestOk() {
    Flux<Flight> fluxFlight = Flux.just(Flight.builder().departureCode("LIS").build());
    when(flightService.getFlight(any(), any(), any())).thenReturn(fluxFlight);

    Flux<Flight> responseBody =
        webTestClient
            .get()
            .uri("/flights/flight?departureCode=LIS&date=2021-12-20&arrivalCode=MDR")
            .exchange()
            .expectStatus()
            .isOk()
            .returnResult(Flight.class)
            .getResponseBody();

    StepVerifier.create(responseBody)
        .expectSubscription()
        .expectNextMatches(flight -> flight.getDepartureCode().equals("LIS"))
        .verifyComplete();
  }

  @Test
  public void getFlightsTestWrongParams() {
    Flux<Flight> fluxFlight = Flux.just(Flight.builder().departureCode("LIS").build());
    when(flightService.getFlight(any(), any(), any())).thenReturn(fluxFlight);

    webTestClient
        .get()
        .uri("/flights/flight?departureCode=LIS&date=2021-12-20")
        .exchange()
        .expectStatus()
        .is4xxClientError();
  }
}
