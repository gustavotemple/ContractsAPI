package com.api.contracts.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class BadRequestException extends IllegalArgumentException {

    public BadRequestException(String message, Exception exception) {
        super(message, exception);
    }

    public BadRequestException(String message) {
        super(message);
    }

}
