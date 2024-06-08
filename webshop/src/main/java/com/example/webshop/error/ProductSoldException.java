package com.example.webshop.error;

import org.apache.coyote.BadRequestException;

public class ProductSoldException extends BadRequestException {
    public ProductSoldException(String massage) {
        super(massage);
    }
}
