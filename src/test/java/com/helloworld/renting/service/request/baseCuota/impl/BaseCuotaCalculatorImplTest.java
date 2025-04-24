package com.helloworld.renting.service.request.baseCuota.impl;

import com.helloworld.renting.service.request.baseCuota.BaseCuotaCalculator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class BaseCuotaCalculatorImplTest {

    private final BaseCuotaCalculator calculator = new BaseCuotaCalculatorImpl();

    @Test
    void testBaseCuotaExactly12Months() {
        Double result = calculator.getBaseCuota(12);
        assertEquals(1.0, result, 0.0001, "Base cuota for 12 months should be 1.0");
    }

    @Test
    void testBaseCuotaLessThan12Months() {
        Double result = calculator.getBaseCuota(6);
        assertEquals(1.6, result, 0.0001, "Base cuota for 6 months should be 1.6");
    }

    @Test
    void testBaseCuotaMoreThan12Months() {
        Double result = calculator.getBaseCuota(15);
        assertEquals(0.91, result, 0.0001, "Base cuota for 15 months should be 0.91");
    }

    @Test
    void testBaseCuotaMaxReductionLimit() {
        Double result = calculator.getBaseCuota(20);
        assertEquals(0.8, result, 0.0001, "Base cuota should not be less than 0.8");
    }

    @Test
    void testBaseCuotaEdgeCaseOneMonth() {
        Double result = calculator.getBaseCuota(1);
        assertEquals(2.1, result, 0.0001, "Base cuota for 1 month should be 2.1");
    }
}