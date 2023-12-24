package com.amadeus.flightSearchApi.business.dto.requests;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchApiRequest {
    @NotBlank
    private String departureAirportCity;

    @NotBlank
    private String arrivalAirportCity;
    @NotNull
    private ZonedDateTime departureDateTime;
    @Nullable
    private ZonedDateTime returnDateTime;
}
