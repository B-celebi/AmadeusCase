package com.amadeus.flightSearchApi.business.exceptionHandlers;

import com.amadeus.flightSearchApi.common.results.ErrorDataResult;
import com.amadeus.flightSearchApi.common.results.ErrorResult;
import com.amadeus.flightSearchApi.common.results.Result;
import com.amadeus.flightSearchApi.common.results.ResultMessage;
import com.amadeus.flightSearchApi.common.utilities.exceptions.BusinessException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ExceptionHandlers {
    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(code= HttpStatus.BAD_REQUEST)
    public Result handleBusinessException(BusinessException businessException){
        return new ErrorResult(businessException.getMessage());
    }
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(code= HttpStatus.BAD_REQUEST)
    public Result handleInputException(HttpMessageNotReadableException httpMessageNotReadableException){
        return new ErrorResult(ResultMessage.BAD_JSON_BODY.toString());
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public Result handleValidException(MethodArgumentNotValidException methodArgumentNotValidException){
        //Validation error mesajlarını mapliyoruz.
        Map<String,String> validationErrors = new HashMap<>();
        for(FieldError fieldError : methodArgumentNotValidException.getBindingResult().getFieldErrors()){
            validationErrors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return new ErrorDataResult<Map<String,String>>(validationErrors,ResultMessage.BAD_INPUT.toString());
    }
    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public Result handleEntityNotFoundException(EntityNotFoundException entityNotFoundException){
        return new ErrorResult("Böyle bir varlık bulunamadı");
    }
}
