package com.starzplay.paymenthandler.advice;

import com.starzplay.paymenthandler.exception.ResourceNotFoundException;
import com.starzplay.paymentprovider.api.model.response.BaseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ResponseExceptionHandler {

    @ExceptionHandler(value = {ResourceNotFoundException.class})
    public ResponseEntity<Object> resourceNotFound(ResourceNotFoundException ex) {
        return new ResponseEntity<>(
                new BaseResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage()), HttpStatus.NOT_FOUND);
    }
}
