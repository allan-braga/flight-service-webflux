package com.emirates.flightservice.client;

import com.emirates.flightservice.model.Flight;
import com.emirates.flightservice.repository.FlightRepository;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component
@RequiredArgsConstructor
public class AirportArrivalsDownstream {

  private final Map<String, List<Flight>> arrivals = new HashMap<>();

  private final FlightRepository flightRepository;

  @PostConstruct
  public void fillDepartures() {
    Set<Flight> flights = flightRepository.getFlights();
    arrivals.putAll(flights.stream().collect(Collectors.groupingBy(this::buildKey)));
  }

  public Flux<Flight> getArrivals(final String date, final String airportCode) {
    String key = buildKey(date, airportCode);
    if (arrivals.containsKey(key)) {
      return Flux.fromStream(arrivals.get(key).stream());
    }
    return Flux.empty();
  }

  private String buildKey(final String date, final String airportCode) {
    return date.concat(";").concat(airportCode);
  }

  private String buildKey(Flight flight) {
    return flight.getDate().toString().concat(";").concat(flight.getArrivalCode());
  }
}
