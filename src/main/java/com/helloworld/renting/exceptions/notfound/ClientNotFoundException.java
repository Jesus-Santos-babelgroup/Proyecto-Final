package com.helloworld.renting.exceptions.notfound;

import com.helloworld.renting.exceptions.RentingException;

public class ClientNotFoundException extends RentingException {
    public ClientNotFoundException(String message) {
        super(message);
    }
}
