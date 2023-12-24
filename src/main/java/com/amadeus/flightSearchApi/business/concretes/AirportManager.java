package com.amadeus.flightSearchApi.business.concretes;

import com.amadeus.flightSearchApi.business.dto.requests.CreateAirportRequest;
import com.amadeus.flightSearchApi.business.dto.requests.DeleteAirportRequest;
import com.amadeus.flightSearchApi.business.dto.requests.UpdateAirportRequest;
import com.amadeus.flightSearchApi.business.dto.responses.GetAllAirportsResponse;
import com.amadeus.flightSearchApi.business.abstracts.AirportBusiness;
import com.amadeus.flightSearchApi.business.rules.AirportManagerRules;
import com.amadeus.flightSearchApi.common.results.*;
import com.amadeus.flightSearchApi.common.utilities.exceptions.BusinessException;
import com.amadeus.flightSearchApi.common.utilities.mappers.ModelMapperBusiness;
import com.amadeus.flightSearchApi.dataAccess.AirportDao;
import com.amadeus.flightSearchApi.entities.Airport;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@NoArgsConstructor
@AllArgsConstructor
public class AirportManager implements AirportBusiness {
    @Autowired
    private AirportDao airportDao;
    @Autowired
    private AirportManagerRules airportManagerRules;
    @Autowired
    private ModelMapperBusiness modelMapperBusiness;
    @Override
    public DataResult<List<GetAllAirportsResponse>> getAll() {
        List<Airport> airports = this.airportDao.findAllByIsActive(true);
        List<GetAllAirportsResponse> getAllAirportsResponses = new ArrayList<>();
        for(Airport airport : airports){
            getAllAirportsResponses.add(this.modelMapperBusiness.forResponse().map(airport,GetAllAirportsResponse.class));
        }
        return new SuccessDataResult<List<GetAllAirportsResponse>>(getAllAirportsResponses);
    }

    @Override
    public Result addAirport(CreateAirportRequest createAirportRequest) {
        //Küçük harflere dönüştürdük.
        createAirportRequest.setCity(createAirportRequest.getCity().toUpperCase());
        //RULES
        this.airportManagerRules.checkIfCityExists(createAirportRequest.getCity());

        Airport airport = this.modelMapperBusiness.forRequest().map(createAirportRequest, Airport.class);

        //ID tanımlıyoruz.
        airport.setId(UUID.randomUUID().toString());
        //isActive durumunu belirtiyoruz. DEFAULT OLARAK TRUE
        airport.setIsActive(true);
        this.airportDao.save(airport);
        return new SuccessResult(ResultMessage.CREATED.toString());
    }

    @Override
    public Result deleteById(DeleteAirportRequest deleteAirportRequest) {
        Airport airport = this.airportDao.findById(deleteAirportRequest.getId()).orElseThrow(() -> new BusinessException("Böyle bir Havalimanı yok."));
        airport.setIsActive(false);
        this.airportDao.save(airport);
        return new SuccessResult(ResultMessage.AIRPORT_HAS_BEEN_DELETED.toString());
    }

    @Override
    public Result updateById(UpdateAirportRequest updateAirportRequest) {
        Airport airport = this.airportDao.findById(updateAirportRequest.getId()).orElseThrow(() ->
                new BusinessException("Böyle bir havalimanı yok."));
        airport.setIsActive(updateAirportRequest.getIsActive());
        this.airportDao.save(airport);
        return new SuccessResult(ResultMessage.SUCCESS.toString());
    }

}
