package com.amadeus.flightSearchApi.business.rules;

import com.amadeus.flightSearchApi.common.utilities.exceptions.BusinessException;
import com.amadeus.flightSearchApi.dataAccess.FlightDao;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.ZonedDateTime;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class SearchApiRules {

    @Autowired
    private FlightDao flightDao;

    public Boolean checkIfCityMatches(String airportCity,String searchAirportCity){
        return airportCity.equals(searchAirportCity);
    }
    public Boolean checkIfDepartureDateTimeDecent(ZonedDateTime departureDateTime, ZonedDateTime searchDateTime){
        if(searchDateTime.isBefore(ZonedDateTime.now())){
            throw new BusinessException("Geçmiş tarihte arama yapılamaz.");
        }
        return Math.abs(Duration.between(departureDateTime,searchDateTime).toHours()) < 24 ;
    }
    public Boolean checkIfReturnDateTimeDecent(ZonedDateTime returnDateTime, ZonedDateTime searchDateTime){
        if(searchDateTime.isBefore(ZonedDateTime.now())){
            throw new BusinessException("Geçmiş tarihte arama yapılamaz.");
        }
        return Math.abs(Duration.between(returnDateTime,searchDateTime).toHours()) < 24  ;
    }
}
