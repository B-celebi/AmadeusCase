package com.amadeus.flightSearchApi.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name="airports")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Airport {
    @Id
    @Column(name="id")
    private String id;

    @NotNull
    @NotEmpty(message = "Bu alan boş bırakılamaz.")
    @Column(name="city")
    private String city;

    @Column(name="is_active")
    private Boolean isActive;

    @OneToMany(mappedBy = "departureAirport")
    private List<Flight> departingFlights;
    @OneToMany(mappedBy = "arrivalAirport")
    private List<Flight> arrivingFlights;

}
