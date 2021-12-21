package com.emirates.flightservice.model;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Flight {
  private String flightNumber;
  private String departureCode;
  private String arrivalCode;
  private LocalDate date;
}
