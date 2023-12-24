package com.amadeus.flightSearchApi.business.dto.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateAirportRequest {
    @NotBlank
    private String id;
    @NotNull
    private Boolean isActive;
}