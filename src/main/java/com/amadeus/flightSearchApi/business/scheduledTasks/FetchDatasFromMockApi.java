package com.amadeus.flightSearchApi.business.scheduledTasks;

import com.amadeus.flightSearchApi.business.abstracts.FlightBusiness;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class FetchDatasFromMockApi {

    @Autowired
    private FlightBusiness flightBusiness;

    @Scheduled(cron="0 0 11 * * ?")
    public void fetchDataAndSave() {
        this.flightBusiness.fetchFromMockApi();
    }

}


