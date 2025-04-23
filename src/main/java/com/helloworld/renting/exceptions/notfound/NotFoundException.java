package com.helloworld.renting.exceptions.notfound;

import com.helloworld.renting.exceptions.RentingException;

public class NotFoundException extends RentingException {
    public NotFoundException(String message) {
        super(message);
    }
}
