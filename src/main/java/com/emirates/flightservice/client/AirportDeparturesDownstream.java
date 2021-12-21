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
public class AirportDeparturesDownstream {

  private final Map<String, List<Flight>> departures = new HashMap<>();

  private final FlightRepository flightRepository;

  @PostConstruct
  public void fillDepartures() {
    Set<Flight> flights = flightRepository.getFlights();
    departures.putAll(flights.stream().collect(Collectors.groupingBy(this::buildKey)));
  }

  public Flux<Flight> getDepartures(final String date, final String airportCode) {
    String key = buildKey(date, airportCode);
    if (departures.containsKey(key)) {
      return Flux.fromStream(departures.get(key).stream());
    }
    return Flux.empty();
  }

  private String buildKey(final String date, final String airportCode) {
    return date.concat(";").concat(airportCode);
  }

  private String buildKey(Flight flight) {
    return flight.getDate().toString().concat(";").concat(flight.getDepartureCode());
  }
}
