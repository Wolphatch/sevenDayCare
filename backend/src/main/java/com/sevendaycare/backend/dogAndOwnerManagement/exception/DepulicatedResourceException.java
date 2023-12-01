package com.sevendaycare.backend.dogAndOwnerManagement.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code= HttpStatus.CONFLICT)
public class DepulicatedResourceException extends RuntimeException{
    public DepulicatedResourceException(String message){
        super(message);
    }
}
