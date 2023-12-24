package com.amadeus.flightSearchApi.dataAccess;

import com.amadeus.flightSearchApi.business.dto.responses.SearchApiResponse;
import com.amadeus.flightSearchApi.entities.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FlightDao extends JpaRepository<Flight,String> {
    boolean existsByDepartureAirportCity(String departureAirportCity);
    boolean existsByArrivalAirportCity(String arrivalAirportCity);

    boolean existsById(String id);
    List<Flight> findAllByReturnDateTimeIsNotNull();
    List<Flight> findAllByDepartureAirportCity(String departureAirportCity);
}
