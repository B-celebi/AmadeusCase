package com.amadeus.flightSearchApi.business.rules;

import com.amadeus.flightSearchApi.common.results.ResultMessage;
import com.amadeus.flightSearchApi.common.utilities.exceptions.BusinessException;
import com.amadeus.flightSearchApi.dataAccess.AirportDao;
import com.amadeus.flightSearchApi.dataAccess.FlightDao;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Service
@NoArgsConstructor
@AllArgsConstructor
public class FlightManagerRules {

    @Autowired
    private FlightDao flightDao;
    @Autowired
    private AirportDao airportDao;
    public void checkIfFlightExists(String id){
        this.flightDao.findById(id).orElseThrow(() -> new RuntimeException(ResultMessage.FLIGHT_COULDNT_FIND.toString()));
    }
    public void checkIfDateTimeBeforeProblem(ZonedDateTime departureDateTime){
        ZonedDateTime now = ZonedDateTime.now();
        if(departureDateTime.isBefore(now)){
            throw new BusinessException(ResultMessage.DEPARTUREDATETIME_IS_BEFORE.toString());
        }
    }
    public void checkIfReturnTimeValid(ZonedDateTime departureDateTime,ZonedDateTime returnDateTime){
        if(returnDateTime.isBefore(departureDateTime)){
            throw new BusinessException(ResultMessage.RETURNDATETIME_IS_BEFORE.toString());
        }
    }
    public void checkPrice(BigDecimal price){
        if(price.doubleValue()<=0){
            throw new BusinessException(ResultMessage.CHECK_PRICE.toString());
        }
    }
    //Havalimanı kayıtlarda yoksa veya isActiv == false ise hata veriyoruz.
    public void checkIfAirportExists(String departureAirportId,String arrivalAirportId){
           if(!this.airportDao.existsById(departureAirportId) || !this.airportDao.existsById(arrivalAirportId)){
               throw new BusinessException(ResultMessage.AIRPORT_ISNT_AVAILABLE.toString());
           }
           if(!this.airportDao.findById(departureAirportId).orElseThrow().getIsActive() || !this.airportDao.findById(arrivalAirportId).orElseThrow().getIsActive()){
               throw new BusinessException(ResultMessage.AIRPORT_ISNT_AVAILABLE.toString());
               //Her zaman airport dönmeyebilir diye orElseThrow yapıyoruz.
           }
    }
    public void checkIfAirportIdString(Object id){
        if(!(id instanceof String)){
            throw new BusinessException(ResultMessage.BAD_INPUT.toString());
        }
    }
    public void checkIfFlightDecent(String departureAirportId, String arrivalAirportId){
        if(departureAirportId.equals(arrivalAirportId)){
            throw new BusinessException(ResultMessage.AIRPORTS_ARE_SAME.toString());
        }
    }
}
