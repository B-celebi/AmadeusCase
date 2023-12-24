package com.amadeus.flightSearchApi.common.results;

import lombok.Getter;

public class DataResult<T> extends Result{

    @Getter
    private T data;

    public DataResult(T data, boolean success, String message) {
        super(success, message);
        this.data = data;
    }

    public DataResult(T data, boolean success) {
        super(success);
        this.data = data;
    }

}

