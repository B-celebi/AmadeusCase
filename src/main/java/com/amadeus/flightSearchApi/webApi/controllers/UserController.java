package com.amadeus.flightSearchApi.webApi.controllers;

import com.amadeus.flightSearchApi.business.abstracts.AppUserBusiness;
import com.amadeus.flightSearchApi.business.dto.requests.CreateUserRequest;
import com.amadeus.flightSearchApi.business.dto.requests.DeleteUserRequest;
import com.amadeus.flightSearchApi.common.results.Result;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
@NoArgsConstructor
public class UserController {

    @Autowired
    private AppUserBusiness appUserBusiness;

    @PostMapping("/add")
    @ResponseStatus(code = HttpStatus.CREATED)
    private Result add(@Valid @RequestBody CreateUserRequest createUserRequest){
        return this.appUserBusiness.add(createUserRequest);
    }

    @PutMapping("/delete")
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    private Result delete(@Valid @RequestBody DeleteUserRequest deleteUserRequest){
        return this.appUserBusiness.delete(deleteUserRequest);
    }

    @GetMapping("/getall")
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    private Result getAll(){
        return this.appUserBusiness.getAll();
    }

}
