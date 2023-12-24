package com.amadeus.flightSearchApi.business.dto.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SearchApiResponse {
    private String departureAirportCity;
    private String arrivalAirportCity;
    private ZonedDateTime departureDateTime;
    private ZonedDateTime returnDateTime;
    private BigDecimal price;
}
