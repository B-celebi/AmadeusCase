package com.amadeus.flightSearchApi.business.concretes;

import com.amadeus.flightSearchApi.business.abstracts.AppUserBusiness;
import com.amadeus.flightSearchApi.business.dto.requests.CreateUserRequest;
import com.amadeus.flightSearchApi.business.dto.requests.DeleteUserRequest;
import com.amadeus.flightSearchApi.business.dto.responses.GetAllUsersResponse;
import com.amadeus.flightSearchApi.business.rules.AppUserManagerRules;
import com.amadeus.flightSearchApi.common.results.*;
import com.amadeus.flightSearchApi.common.utilities.exceptions.BusinessException;
import com.amadeus.flightSearchApi.common.utilities.mappers.ModelMapperBusiness;
import com.amadeus.flightSearchApi.dataAccess.AppUserDao;
import com.amadeus.flightSearchApi.entities.AppUser;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class AppUserManager implements AppUserBusiness {

    @Autowired
    private AppUserManagerRules appUserManagerRules;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AppUserDao appUserDao;
    @Autowired
    private ModelMapperBusiness modelMapperBusiness;
    @Override
    public DataResult<List<GetAllUsersResponse>> getAll() {
        List<AppUser> appUsers = this.appUserDao.findAll();
        List<GetAllUsersResponse> getAllUsersResponses = new ArrayList<>();
        for(AppUser u : appUsers) {
            getAllUsersResponses.add(this.modelMapperBusiness.forResponse().map(u, GetAllUsersResponse.class));
        };
        return new SuccessDataResult(getAllUsersResponses, ResultMessage.SUCCESS.toString());
        }

    @Override
    public Result add(CreateUserRequest createUserRequest) {
        this.appUserManagerRules.checkIfUsernameUnique(createUserRequest.getUsername());
        this.appUserManagerRules.checkIfRoleDecent(createUserRequest.getRole().toUpperCase());
        createUserRequest.setPassword(this.passwordEncoder.encode(createUserRequest.getPassword()));
        String incomingRole = createUserRequest.getRole().toUpperCase();

        StringBuilder strBuilder = new StringBuilder();
        strBuilder.append("ROLE_"+ incomingRole);
        createUserRequest.setRole(strBuilder.toString());

        AppUser appUser = this.modelMapperBusiness.forRequest().map(createUserRequest,AppUser.class);
        appUser.setId(UUID.randomUUID().toString());
        appUser.setActive(true);
        this.appUserDao.save(appUser);
        return new SuccessResult();
    }

    @Override
    public Result delete(DeleteUserRequest deleteUserRequest) {
        AppUser appUser = this.appUserDao.findById(deleteUserRequest.getId()).orElseThrow(() ->
                new BusinessException("Böyle bir kullanıcı yok"));
        appUser.setActive(false);
        this.appUserDao.save(appUser);
        return new SuccessResult();
    }
}
