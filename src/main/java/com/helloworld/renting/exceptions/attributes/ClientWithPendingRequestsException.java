package com.helloworld.renting.exceptions.attributes;

import com.helloworld.renting.exceptions.RentingException;

public class ClientWithPendingRequestsException extends RentingException {
    public ClientWithPendingRequestsException(String message) {
        super(message);
    }
}
