package com.example.webshop.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NoSellerException extends Throwable{
    public NoSellerException(String message)  {
        super(message);
    }
}
