package com.amadeus.flightSearchApi.common.results;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
public enum ResultMessage {

    SUCCESS("İstek Başarılı."),
    CREATED("Başarılı bir şekilde oluşturuldu"),
    CHECK_PRICE("Fiyat ücretsiz ve aşağısı olamaz."),
    AIRPORTS_ARE_SAME("Aynı havalimanına uçulamaz."),
    RETURNDATETIME_IS_BEFORE("Dönüş tarihi, gidiş tarihinden önce olamaz"),
    DEPARTUREDATETIME_IS_BEFORE("Geçmiş tarihte randevu alınamaz"),
    BAD_INPUT("Girdiğiniz değer tiplerini kontrol edin."),
    BAD_JSON_BODY("JSON syntax hatası, gövdeyi kontrol edin."),
    REQUESTBODY_CHECK("İstek geçersiz"),
    AIRPORT_ALREADY_ADDED("Boyle bir havalimani zaten var"),
    AIRPORT_HAS_BEEN_DELETED("Havalimanı başarıyla silindi."),
    AIRPORT_ISNT_AVAILABLE("Boyle bir havalimani yok."),
    SEARCH_COULDNT_FIND("Aradığınız kriterlere uygun uçuş bulunamadı."),
    FLIGHT_COULDNT_FIND("Böyle bir uçuş yok"),
    FLIGHT_ALREADY_ADDED("Boyle bir uçuş zaten var");
    private String message;

    public String toString(){
        return this.message;
    }
}






