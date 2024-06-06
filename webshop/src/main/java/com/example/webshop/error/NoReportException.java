package com.example.webshop.error;

import org.apache.coyote.BadRequestException;

public class NoReportException extends BadRequestException {
    public NoReportException(String massage)  {
        super(massage);
    }
}
