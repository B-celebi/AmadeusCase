package com.amadeus.flightSearchApi.webApi.controllers;

import com.amadeus.flightSearchApi.business.dto.requests.CreateAirportRequest;
import com.amadeus.flightSearchApi.business.dto.requests.DeleteAirportRequest;
import com.amadeus.flightSearchApi.business.dto.requests.UpdateAirportRequest;
import com.amadeus.flightSearchApi.business.dto.responses.GetAllAirportsResponse;
import com.amadeus.flightSearchApi.business.abstracts.AirportBusiness;
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
@RequestMapping("/api/airports")
@AllArgsConstructor
@NoArgsConstructor
public class AirportController {
    @Autowired
    private AirportBusiness airportBusiness;

    @GetMapping("/getall")
    private DataResult<List<GetAllAirportsResponse>> getAll(){
        return this.airportBusiness.getAll();
    }
    @PostMapping("/add")
    @ResponseStatus(code= HttpStatus.CREATED)
    private Result add(@Valid @RequestBody CreateAirportRequest createAirportRequest){
        return this.airportBusiness.addAirport(createAirportRequest);
    }

    @PutMapping("/delete")
    private Result deleteById(@Valid @RequestBody DeleteAirportRequest deleteAirportRequest){
        return this.airportBusiness.deleteById(deleteAirportRequest);
    }

    @PutMapping("/update")
    private Result updateById(@Valid @RequestBody UpdateAirportRequest updateAirportRequest){
        return this.airportBusiness.updateById(updateAirportRequest);
    }


}
