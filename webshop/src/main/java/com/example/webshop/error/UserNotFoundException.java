package com.example.webshop.error;

import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends BadRequestException {
    public UserNotFoundException(String message) {
        super(message);
    }

}
