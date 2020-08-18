package com.api.contracts.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class NotFoundException extends IllegalArgumentException {

    public NotFoundException(String message, Exception exception) {
        super(message, exception);
    }

    public NotFoundException(String message) {
        super(message);
    }

}
