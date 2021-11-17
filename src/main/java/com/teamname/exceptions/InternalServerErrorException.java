package com.teamname.exceptions;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus
public class InternalServerErrorException extends RuntimeException {

    public InternalServerErrorException(String message){
        super(message);
    }

}
