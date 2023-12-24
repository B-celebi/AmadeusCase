package com.amadeus.flightSearchApi.business.dto.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateAirportRequest {
    @NotBlank
    @Size(min = 3, message = "Şehir en az 3 karakterli olmalıdır.")
    private String city;
}
