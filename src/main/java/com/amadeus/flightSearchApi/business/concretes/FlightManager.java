package com.amadeus.flightSearchApi.business.concretes;

import com.amadeus.flightSearchApi.business.abstracts.FlightBusiness;
import com.amadeus.flightSearchApi.business.dto.requests.CreateFlightRequest;
import com.amadeus.flightSearchApi.business.dto.requests.DeleteFlightRequest;
import com.amadeus.flightSearchApi.business.dto.requests.SearchApiRequest;
import com.amadeus.flightSearchApi.business.dto.requests.UpdateFlightRequest;
import com.amadeus.flightSearchApi.business.dto.responses.GetAllFlightsResponse;
import com.amadeus.flightSearchApi.business.dto.responses.SearchApiResponse;
import com.amadeus.flightSearchApi.business.rules.FlightManagerRules;
import com.amadeus.flightSearchApi.business.rules.SearchApiRules;
import com.amadeus.flightSearchApi.common.results.*;
import com.amadeus.flightSearchApi.common.utilities.exceptions.BusinessException;
import com.amadeus.flightSearchApi.common.utilities.mappers.ModelMapperBusiness;
import com.amadeus.flightSearchApi.dataAccess.FlightDao;
import com.amadeus.flightSearchApi.entities.Flight;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class FlightManager implements FlightBusiness {

    //Loggerı FlightManager içerisinde tanımlıyoruz.
    Logger logger = LoggerFactory.getLogger(FlightManager.class);

    /*Uçuşların her zaman aynı URL'den geleceğini varsayarak final keywordu ile bir değişken oluşturuyoruz.
    Mock Api gerçek bir API olarak yazılmalı ve her gün veriler güncellenmeli. Eğer diğer gün uçuş
    yok ise o gün boş girilebilir.*/
    private final String API_ENDPOINT = "https://a79d3d9aae5947028ef6c8b4fa1d58a2.api.mockbin.io/";

    @Autowired
    private FlightDao flightDao;
    @Autowired
    private FlightManagerRules flightManagerRules;
    @Autowired
    private ModelMapperBusiness modelMapperBusiness;
    @Autowired
    private SearchApiRules searchApiRules;
    @Override
    public DataResult<List<GetAllFlightsResponse>> getAll() {
        List<Flight> flights = this.flightDao.findAll();
        List<GetAllFlightsResponse> getAllFlightsResponses = new ArrayList<>();
        for (Flight f : flights){
            getAllFlightsResponses.add(this.modelMapperBusiness.forResponse().map(f,GetAllFlightsResponse.class));
        }
        return new SuccessDataResult<>(getAllFlightsResponses);
    }

    @Override
    public Result addFlight(CreateFlightRequest createFlightRequest) {
        //Gidis zamani simdiden once olamaz.
        this.flightManagerRules.checkIfDateTimeBeforeProblem(createFlightRequest.getDepartureDateTime());
        //Donus zamani gidisten once olamaz
        if(createFlightRequest.getReturnDateTime() != null) {
            this.flightManagerRules.checkIfReturnTimeValid(createFlightRequest.getDepartureDateTime(), createFlightRequest.getReturnDateTime());
        }
        //Kalkis ve Inis havalimani ayni olamaz
        this.flightManagerRules.checkIfFlightDecent(createFlightRequest.getDepartureAirportId(),createFlightRequest.getArrivalAirportId());
        //Fiyat 0 ve asagisi olamaz
        this.flightManagerRules.checkPrice(createFlightRequest.getPrice());
        //Uçuş ID'sini kontrol et. Havalimanı kayıtlarda yoksa veya isActiv == false ise hata veriyoruz.
        this.flightManagerRules.checkIfAirportExists(createFlightRequest.getDepartureAirportId(),createFlightRequest.getArrivalAirportId());
        //Airport IDler string değilse uyarı veriyoruz.
        this.flightManagerRules.checkIfAirportIdString(createFlightRequest.getDepartureAirportId());
        //DepartureAirportId tip kontrolü
        this.flightManagerRules.checkIfAirportIdString(createFlightRequest.getDepartureAirportId());
        //ArrivalAirportId tip kontrolü
        this.flightManagerRules.checkIfAirportIdString(createFlightRequest.getArrivalAirportId());

        //ModelMapper ile mapliyoruz.
        Flight flight = modelMapperBusiness.forRequest().map(createFlightRequest,Flight.class);
        //ID veriyoruz.
        flight.setId(UUID.randomUUID().toString());
        this.flightDao.save(flight);
        return new SuccessResult(ResultMessage.CREATED.toString());
    }

    @Override
    public Result searchFlights(SearchApiRequest searchApiRequest) {
        //Türkçe harf kontrolleri için önce büyük harflere çeviriyoruz.
        searchApiRequest.setArrivalAirportCity(searchApiRequest.getArrivalAirportCity().toUpperCase());
        searchApiRequest.setDepartureAirportCity(searchApiRequest.getDepartureAirportCity().toUpperCase());
        List<SearchApiResponse> searchApiResponses;
        List<SearchApiResponse> alternativeFlights = new ArrayList<>(); //to avoid Compiler Error.
        if(searchApiRequest.getReturnDateTime() == null){
            System.out.println("tek yönlü uçuş bileti");
            List<Flight> flights = this.flightDao.findAll();
            //Tek yönlü uçuş kuralları
            searchApiResponses = flights.stream().filter((flight) ->
                    this.searchApiRules.checkIfCityMatches(flight.getDepartureAirport().getCity(),searchApiRequest.getDepartureAirportCity()) &&
                            this.searchApiRules.checkIfCityMatches(flight.getArrivalAirport().getCity(),searchApiRequest.getArrivalAirportCity()) &&
                    this.searchApiRules.checkIfDepartureDateTimeDecent(flight.getDepartureDateTime(),searchApiRequest.getDepartureDateTime())

            ).map(filteredFlight -> this.modelMapperBusiness.forResponse().map(filteredFlight,SearchApiResponse.class)).collect(Collectors.toList());

            //Dönüş tarihi ve lokasyonları uyan GİDİŞ biletleri için.
            alternativeFlights = flights.stream().filter((flight) ->
                            this.searchApiRules.checkIfCityMatches(flight.getArrivalAirport().getCity(),searchApiRequest.getDepartureAirportCity()) &&
                                    this.searchApiRules.checkIfCityMatches(flight.getDepartureAirport().getCity(),searchApiRequest.getArrivalAirportCity()) &&
                                    this.searchApiRules.checkIfDepartureDateTimeDecent(flight.getReturnDateTime(),searchApiRequest.getDepartureDateTime())
                    ).map(filteredFlight -> {
                        SearchApiResponse sar = new SearchApiResponse();
                        sar.setPrice(filteredFlight.getPrice().divide(new BigDecimal("2")));
                        sar.setDepartureDateTime(filteredFlight.getReturnDateTime());
                        sar.setArrivalAirportCity(filteredFlight.getDepartureAirport().getCity());
                        sar.setDepartureAirportCity(filteredFlight.getArrivalAirport().getCity());
                        return sar;
            }).toList();
            searchApiResponses.addAll(alternativeFlights);

        }else{
            //cift yonlu ucus kuralları
            System.out.println("Çift yönlü uçuş bileti");
            List<Flight> flights = this.flightDao.findAllByReturnDateTimeIsNotNull();
            searchApiResponses = flights.stream().filter((flight) ->
                    this.searchApiRules.checkIfCityMatches(flight.getDepartureAirport().getCity(),searchApiRequest.getDepartureAirportCity()) &&
                            this.searchApiRules.checkIfCityMatches(flight.getArrivalAirport().getCity(),searchApiRequest.getArrivalAirportCity()) &&
                            this.searchApiRules.checkIfDepartureDateTimeDecent(flight.getDepartureDateTime(),searchApiRequest.getDepartureDateTime()) &&
                            this.searchApiRules.checkIfReturnDateTimeDecent(flight.getReturnDateTime(),searchApiRequest.getReturnDateTime())
            ).map(filteredFlight -> this.modelMapperBusiness.forResponse().map(filteredFlight,SearchApiResponse.class)).collect(Collectors.toList());
        }
        if(searchApiResponses.isEmpty()) {
            throw new BusinessException(ResultMessage.SEARCH_COULDNT_FIND.toString());
        }
        return new SuccessDataResult<>(searchApiResponses,ResultMessage.SUCCESS.toString());
    }

    @Override
    public Result deleteFlight(DeleteFlightRequest deleteFlightRequest) {
        this.flightManagerRules.checkIfFlightExists(deleteFlightRequest.getId());
        this.flightDao.deleteById(deleteFlightRequest.getId());
        return new SuccessResult(ResultMessage.SUCCESS.toString());
    }

    @Override
    public Result updateFlight(UpdateFlightRequest updateFlightRequest) {
        this.flightManagerRules.checkIfFlightExists(updateFlightRequest.getId());
        this.flightManagerRules.checkIfDateTimeBeforeProblem(updateFlightRequest.getDepartureDateTime());
        if(updateFlightRequest.getReturnDateTime() != null) {
            this.flightManagerRules.checkIfReturnTimeValid(updateFlightRequest.getDepartureDateTime(), updateFlightRequest.getReturnDateTime());
        }
        Flight flight = this.modelMapperBusiness.forRequest().map(updateFlightRequest,Flight.class);
        this.flightDao.save(flight);
        return new SuccessDataResult<>(flight,ResultMessage.SUCCESS.toString());
    }

    @Override
    public void fetchFromMockApi() {
        logger.trace("Veriler çekildi");
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<CreateFlightRequest[]> responseEntity = restTemplate.getForEntity(API_ENDPOINT, CreateFlightRequest[].class);
        CreateFlightRequest[] createFlightRequestsArray = responseEntity.getBody();

        List<CreateFlightRequest> createFlightRequests = new ArrayList<>();
        if(createFlightRequestsArray != null){
            for (CreateFlightRequest createFlightRequest : createFlightRequestsArray){
                createFlightRequests.add(this.modelMapperBusiness.forRequest().map(createFlightRequest, CreateFlightRequest.class));
            }
        }
        List<Flight> fetchedFlights = new ArrayList<>();
        for(CreateFlightRequest createFlightRequest : createFlightRequests){
            fetchedFlights.add(this.modelMapperBusiness.forRequest().map(createFlightRequest,Flight.class));
        }
        for(Flight flight : fetchedFlights){
            flight.setId(UUID.randomUUID().toString());
            this.flightDao.save(flight);
        }

    }


}
