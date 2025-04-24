package com.helloworld.renting.exceptions;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RentingException extends RuntimeException {
    private static final Logger logger = LogManager.getLogger();

    public RentingException(String message) {
        super(message);
        logger.error(message);
    }
}
