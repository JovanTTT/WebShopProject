package com.example.webshop.error;

import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ProductCanNotBeeChanged extends BadRequestException {
    public ProductCanNotBeeChanged(String massage) {
        super(massage);
    }
}
