package com.helloworld.renting.service.request.approval;

import com.helloworld.renting.dto.RentingRequestDto;

public interface Rules {
    boolean conditionMet(RentingRequestDto rentingRequestDto);

    public String getName();
}