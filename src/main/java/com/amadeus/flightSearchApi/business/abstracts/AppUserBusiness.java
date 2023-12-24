package com.amadeus.flightSearchApi.business.abstracts;

import com.amadeus.flightSearchApi.business.dto.requests.CreateUserRequest;
import com.amadeus.flightSearchApi.business.dto.requests.DeleteUserRequest;
import com.amadeus.flightSearchApi.business.dto.responses.GetAllUsersResponse;
import com.amadeus.flightSearchApi.common.results.DataResult;
import com.amadeus.flightSearchApi.common.results.Result;

import java.util.List;

public interface AppUserBusiness {
    DataResult<List<GetAllUsersResponse>> getAll();
    Result add(CreateUserRequest createUserRequest);
    Result delete(DeleteUserRequest deleteUserRequest);
}
