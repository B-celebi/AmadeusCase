package com.amadeus.flightSearchApi.business.dto.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateFlightRequest {

    @NotBlank
    private String id;
    @NotNull
    private ZonedDateTime departureDateTime;
    @NotNull
    private ZonedDateTime returnDateTime;
    @NotNull
    private BigDecimal price;
}
