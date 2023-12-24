package com.amadeus.flightSearchApi.business.abstracts;

import com.amadeus.flightSearchApi.business.dto.requests.CreateFlightRequest;
import com.amadeus.flightSearchApi.business.dto.requests.DeleteFlightRequest;
import com.amadeus.flightSearchApi.business.dto.requests.SearchApiRequest;
import com.amadeus.flightSearchApi.business.dto.requests.UpdateFlightRequest;
import com.amadeus.flightSearchApi.business.dto.responses.GetAllFlightsResponse;
import com.amadeus.flightSearchApi.common.results.DataResult;
import com.amadeus.flightSearchApi.common.results.Result;

import java.util.List;

public interface FlightBusiness {
    DataResult<List<GetAllFlightsResponse>> getAll();
    Result addFlight(CreateFlightRequest createFlightRequest);

    Result searchFlights(SearchApiRequest searchApiRequest);

    Result deleteFlight(DeleteFlightRequest deleteFlightRequest);

    Result updateFlight(UpdateFlightRequest updateFlightRequest);

    void fetchFromMockApi();
}
