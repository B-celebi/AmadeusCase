package com.amadeus.flightSearchApi.webApi.controllers;

import com.amadeus.flightSearchApi.business.dto.requests.CreateFlightRequest;
import com.amadeus.flightSearchApi.business.dto.requests.DeleteFlightRequest;
import com.amadeus.flightSearchApi.business.dto.requests.SearchApiRequest;
import com.amadeus.flightSearchApi.business.dto.requests.UpdateFlightRequest;
import com.amadeus.flightSearchApi.business.dto.responses.GetAllFlightsResponse;
import com.amadeus.flightSearchApi.business.abstracts.FlightBusiness;
import com.amadeus.flightSearchApi.common.results.DataResult;
import com.amadeus.flightSearchApi.common.results.Result;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/flights")
@AllArgsConstructor
@NoArgsConstructor
public class FlightController {

    @Autowired
    private FlightBusiness flightBusiness;

    @GetMapping("/getall")
    private DataResult<List<GetAllFlightsResponse>> getAll(){
        return this.flightBusiness.getAll();
    }

    @PostMapping("/add")
    @ResponseStatus(code= HttpStatus.CREATED)
    private Result add(@Valid @RequestBody CreateFlightRequest createFlightRequest){
        return this.flightBusiness.addFlight(createFlightRequest);
    }

    @DeleteMapping("/delete")
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    private Result delete(@Valid @RequestBody DeleteFlightRequest deleteFlightRequest){
        return this.flightBusiness.deleteFlight(deleteFlightRequest);
    }
    @PutMapping("/update")
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    private Result update(@Valid @RequestBody UpdateFlightRequest updateFlightRequest){
        return this.flightBusiness.updateFlight(updateFlightRequest);
    }

    @GetMapping("/searchapi")
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    private Result searchFlights(@Valid SearchApiRequest searchApiRequest){
        return this.flightBusiness.searchFlights(searchApiRequest) ;
    }
}
