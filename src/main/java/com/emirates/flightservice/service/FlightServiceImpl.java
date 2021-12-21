package com.emirates.flightservice.service;

import com.emirates.flightservice.client.AirportArrivalsDownstream;
import com.emirates.flightservice.client.AirportDeparturesDownstream;
import com.emirates.flightservice.model.Flight;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
@Slf4j
public class FlightServiceImpl implements FlightService {

  private final AirportDeparturesDownstream departuresDownstream;
  private final AirportArrivalsDownstream arrivalsDownstream;

  @Override
  public Flux<Flight> getFlight(String date, String departureCode, String arrivalCode) {
    return Flux.merge(
            departuresDownstream.getDepartures(date, departureCode),
            arrivalsDownstream.getArrivals(date, arrivalCode),
            // Mock the others downstream
            Flux.just(Flight.builder().departureCode("").arrivalCode("").build())
                .delayElements(Duration.ofMillis(500)),
            Flux.just(Flight.builder().departureCode("").arrivalCode("").build())
                .delayElements(Duration.ofMillis(600)),
            Flux.just(Flight.builder().departureCode("").arrivalCode("").build())
                .delayElements(Duration.ofMillis(500)))
        .filter(
            flightModel ->
                flightModel.getDepartureCode().equals(departureCode)
                    && flightModel.getArrivalCode().equals(arrivalCode));
  }
}
