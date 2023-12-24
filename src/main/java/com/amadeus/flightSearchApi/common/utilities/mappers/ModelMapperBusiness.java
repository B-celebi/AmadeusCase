package com.amadeus.flightSearchApi.common.utilities.mappers;

import org.modelmapper.ModelMapper;

public interface ModelMapperBusiness {
    ModelMapper forRequest();
    ModelMapper forResponse();
}
