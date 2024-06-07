package com.example.webshop.error;

import org.apache.coyote.BadRequestException;

public class ProductNotFoundException extends BadRequestException {
    public ProductNotFoundException(String message)  {
        super(message);
    }
}
