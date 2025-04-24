package com.helloworld.renting.service.request.approval;

import com.helloworld.renting.entities.Client;
import com.helloworld.renting.entities.RentingRequest;

public interface Rules {

    public boolean conditionMet(RentingRequest rentingRequest);
}