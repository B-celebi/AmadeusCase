package com.amadeus.flightSearchApi.dataAccess;

import com.amadeus.flightSearchApi.entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppUserDao extends JpaRepository<AppUser,String> {
    public Boolean existsAppUserByUsername(String username);
}
