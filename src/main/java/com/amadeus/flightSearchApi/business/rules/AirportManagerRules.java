package com.amadeus.flightSearchApi.business.rules;

import com.amadeus.flightSearchApi.common.results.ResultMessage;
import com.amadeus.flightSearchApi.common.utilities.exceptions.BusinessException;
import com.amadeus.flightSearchApi.dataAccess.AirportDao;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@NoArgsConstructor
@AllArgsConstructor
public class AirportManagerRules {
    @Autowired
    private AirportDao airportDao;

    public void checkIfCityExists(String city){
        if(this.airportDao.existsByCity(city)){
            throw new BusinessException(ResultMessage.AIRPORT_ALREADY_ADDED.toString());
        }
    }
}
