package com.emirates.flightservice.api;

import com.emirates.flightservice.model.Flight;
import com.emirates.flightservice.service.FlightService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/flights")
@RequiredArgsConstructor
public class FlightController {

  private final FlightService flightService;

  @GetMapping("/flight")
  public Flux<Flight> getFlight(@RequestParam("date") String date,
      @RequestParam("departureCode") String departureCode,
      @RequestParam("arrivalCode") String arrivalCode) {
    return flightService.getFlight(date, departureCode, arrivalCode);
  }
}
