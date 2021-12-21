package com.emirates.flightservice.repository;

import com.emirates.flightservice.model.Flight;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
@Slf4j
public class FlightRepository {

  private final ObjectMapper mapper;

  private Set<Flight> flights = new HashSet<>();

  @PostConstruct
  public void createFlights() {
    TypeReference<Set<Flight>> typeReference = new TypeReference<>() {};
    InputStream inputStream = TypeReference.class.getResourceAsStream("/flights.json");
    try {
      flights = mapper.readValue(inputStream, typeReference);
    } catch (IOException e) {
      log.error("Unable to create flights: ", e);
    }
  }

  public Set<Flight> getFlights() {
    return flights;
  }
}
