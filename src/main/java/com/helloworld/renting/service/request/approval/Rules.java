package com.helloworld.renting.service.request.approval;

import com.helloworld.renting.entities.Request;

public interface Rules {
    boolean conditionMet(Request request);
}