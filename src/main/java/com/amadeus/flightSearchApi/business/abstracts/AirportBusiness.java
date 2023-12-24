package com.amadeus.flightSearchApi.business.abstracts;

import com.amadeus.flightSearchApi.business.dto.requests.CreateAirportRequest;
import com.amadeus.flightSearchApi.business.dto.requests.DeleteAirportRequest;
import com.amadeus.flightSearchApi.business.dto.requests.UpdateAirportRequest;
import com.amadeus.flightSearchApi.business.dto.responses.GetAllAirportsResponse;
import com.amadeus.flightSearchApi.common.results.DataResult;
import com.amadeus.flightSearchApi.common.results.Result;

import java.util.List;

public interface AirportBusiness {
    DataResult<List<GetAllAirportsResponse>> getAll();
    Result addAirport(CreateAirportRequest createAirportRequest);
    Result deleteById(DeleteAirportRequest deleteAirportRequest);

    Result updateById(UpdateAirportRequest updateAirportRequest);
}
