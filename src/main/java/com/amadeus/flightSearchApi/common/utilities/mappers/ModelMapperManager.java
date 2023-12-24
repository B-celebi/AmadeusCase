package com.amadeus.flightSearchApi.common.utilities.mappers;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ModelMapperManager implements ModelMapperBusiness {
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public ModelMapper forRequest() {
        this.modelMapper.getConfiguration()
                .setAmbiguityIgnored(true)
                .setMatchingStrategy(MatchingStrategies.STANDARD);
                /*
                Eğer karşılığı yok ise hata ver.
                */
        return this.modelMapper;
    }

    @Override
    public ModelMapper forResponse() {
        this.modelMapper.getConfiguration()
                .setAmbiguityIgnored(true)
                .setMatchingStrategy(MatchingStrategies.LOOSE);
                /*
                Eğer DTO'da veritabanından gelen columna karşılık
                bir attribute yok ise o columnu yoksay ve hata verme.
                */
        return this.modelMapper;
    }
}
