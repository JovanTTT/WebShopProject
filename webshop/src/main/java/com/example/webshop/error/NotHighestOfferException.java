package com.example.webshop.error;

import org.apache.coyote.BadRequestException;

public class NotHighestOfferException extends BadRequestException {
    public NotHighestOfferException(String massage) {
        super(massage);
    }
}
