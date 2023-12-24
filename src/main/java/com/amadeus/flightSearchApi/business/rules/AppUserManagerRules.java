package com.amadeus.flightSearchApi.business.rules;

import com.amadeus.flightSearchApi.common.utilities.exceptions.BusinessException;
import com.amadeus.flightSearchApi.dataAccess.AppUserDao;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class AppUserManagerRules {
    @Autowired
    private AppUserDao appUserDao;
    public void checkIfRoleDecent(String role){
        Boolean isDecent = false;
        String[] roles = new String[]{"ADMIN","USER"};
        for(String r : roles){
            if(r.equals(role)){
                isDecent = true;
                break;
            }
        }
        if(isDecent == false){
            throw new BusinessException("Bu rolde bir kullanıcı oluşturulamaz.");
        }
    }
    public void checkIfUsernameUnique(String username){
        if(this.appUserDao.existsAppUserByUsername(username)){
            throw new BusinessException("Böyle bir kullanıcı zaten var.");
        }
    }
}
