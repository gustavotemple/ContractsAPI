package com.api.contracts.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT)
public class DuplicateException extends IllegalArgumentException {

    public DuplicateException(String message, Exception exception) {
        super(message, exception);
    }

    public DuplicateException(String message) {
        super(message);
    }
}
