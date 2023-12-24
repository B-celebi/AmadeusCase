package com.amadeus.flightSearchApi.business.dto.requests;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateFlightRequest {
    @NotNull
    private ZonedDateTime departureDateTime;

    @Nullable
    private ZonedDateTime returnDateTime;

    @NotNull
    private BigDecimal price;

    @NotBlank
    private String departureAirportId;

    @NotBlank
    private String arrivalAirportId;
}
