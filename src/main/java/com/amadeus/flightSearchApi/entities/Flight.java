package com.amadeus.flightSearchApi.entities;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Entity
@Table(name="flights")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Flight {
    @Id
    @Column(name="id")
    private String id;


    @NotNull
    @Column(name="departure_datetime")
    private ZonedDateTime departureDateTime;

    @Nullable
    @Column(name="return_datetime")
    private ZonedDateTime returnDateTime;

    @NotNull
    @Column(name="price")
    private BigDecimal price;

    @NotBlank
    @NotEmpty
    @ManyToOne
    @JoinColumn(name="departure_airport_id")
    private Airport departureAirport;


    @NotBlank
    @NotEmpty
    @ManyToOne
    @JoinColumn(name="arrival_airport_id")
    private Airport arrivalAirport;
}
