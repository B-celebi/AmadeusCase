package com.amadeus.flightSearchApi.common.results;

import lombok.Getter;

public class Result {
    private boolean success;
    @Getter
    private String message;

    public Result(boolean success){
        this.success = success;
        this.message = ResultMessage.SUCCESS.toString();
    }

    public Result(boolean success, String message){
        this(success);
        this.message = message;
    }


    public boolean isSuccess(){
        return this.success;
    }

}

