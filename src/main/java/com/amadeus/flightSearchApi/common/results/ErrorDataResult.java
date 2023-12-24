package com.amadeus.flightSearchApi.common.results;

public class ErrorDataResult<T> extends DataResult<T>{

    public ErrorDataResult(T data, String message) {
        super(data, false, message);
    }

    public ErrorDataResult(T data){
        super(data,false);
    }

}
