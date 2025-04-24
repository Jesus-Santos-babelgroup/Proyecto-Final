package com.helloworld.renting.service.request.approval.rules.denial;

import com.helloworld.renting.entities.Request;
import com.helloworld.renting.exceptions.attributes.NullValueException;
import com.helloworld.renting.service.request.approval.Rules;
import org.springframework.stereotype.Service;

@Service
public class DenialRule2 implements Rules {


    public DenialRule2() {
    }

    @Override
    public boolean conditionMet(Request request) {
        if (request == null) {
            throw new NullValueException("Request is null.");
        }

        Integer idClient = request.getIdClient();
        if (idClient == null) {
            throw new NullValueException("ID client is null.");
        }

        // get client by idClient
        //if (client == null) {
        //}

        // get score from client
        // if (score == null) {}

        return score >= 6;
    }
}
