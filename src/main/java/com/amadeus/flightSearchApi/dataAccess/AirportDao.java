package com.amadeus.flightSearchApi.dataAccess;

import com.amadeus.flightSearchApi.entities.Airport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AirportDao extends JpaRepository<Airport,String> {
    boolean existsById(String id);
    boolean existsByCity(String city);
    List<Airport> findAllByIsActive(boolean isActive);
}
