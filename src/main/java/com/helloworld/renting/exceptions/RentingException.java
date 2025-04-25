package com.helloworld.renting.exceptions;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class RentingException extends RuntimeException {

    private static final Logger logger = LogManager.getLogger();

    public RentingException(String message) {
        super(message);
        logger.error(message);
    }
}
