package com.emirates.flightservice.service;

import static org.mockito.Mockito.when;

import com.emirates.flightservice.client.AirportArrivalsDownstream;
import com.emirates.flightservice.client.AirportDeparturesDownstream;
import com.emirates.flightservice.model.Flight;
import java.time.LocalDate;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@RunWith(SpringRunner.class)
public class FlightServiceTest {

  @Mock private AirportDeparturesDownstream departuresDownstream;

  @Mock private AirportArrivalsDownstream arrivalsDownstream;

  @InjectMocks private FlightServiceImpl flightService;

  private static final String DEPARTURE = "LIS";
  private static final String ARRIVAL = "MDR";
  private static final String DATE = LocalDate.now().toString();

  @Test
  @DisplayName("findFlights returns a flux of flights")
  public void getFlights_ReturnFluxOfFlights_WhenSuccessful() {
    Flux<Flight> flightFlux = Flux.just(flight());
    when(departuresDownstream.getDepartures(DATE, DEPARTURE)).thenReturn(flightFlux);
    when(arrivalsDownstream.getArrivals(DATE, ARRIVAL)).thenReturn(flightFlux);

    StepVerifier.create(flightService.getFlight(DATE, DEPARTURE, ARRIVAL))
        .expectSubscription()
        .expectNext(flight())
        .thenConsumeWhile(t -> true)
        .verifyComplete();
  }

  private Flight flight() {
    return Flight.builder()
        .departureCode(DEPARTURE)
        .arrivalCode(ARRIVAL)
        .flightNumber("0001")
        .date(LocalDate.now())
        .build();
  }
}
