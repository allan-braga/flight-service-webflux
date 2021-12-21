package com.emirates.flightservice.service;

import com.emirates.flightservice.model.Flight;
import reactor.core.publisher.Flux;

public interface FlightService {

  Flux<Flight> getFlight(String date, String departureCode, String arrivalCode);
}
