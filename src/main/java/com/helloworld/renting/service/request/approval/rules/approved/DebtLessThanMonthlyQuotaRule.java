package com.helloworld.renting.service.request.approval.rules.approved;

import com.helloworld.renting.entities.Client;
import com.helloworld.renting.entities.RentingRequest;

import java.util.ArrayList;
import java.util.List;

public class DebtLessThanMonthlyQuotaRule extends ApprovedRule{

    @Override
    public boolean conditionMet(RentingRequest rentingRequest) {
        Long idClient = rentingRequest.getClientId();
        //TODO: obtener cliente real
        Client client = new Client();
        //TODO: obtener deuda real con client.getNif();
        Double debt = 100.;
        return debt < rentingRequest.getQuotaBaseMonthlyFee().doubleValue();
    }

}