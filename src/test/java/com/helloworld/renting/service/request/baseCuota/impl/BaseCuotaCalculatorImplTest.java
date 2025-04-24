package com.helloworld.renting.service.request.baseCuota.impl;

import com.helloworld.renting.service.request.baseCuota.BaseCuotaCalculator;
import com.helloworld.renting.service.request.baseCuota.BaseCuotaProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BaseCuotaCalculatorImplTest {

    private BaseCuotaCalculator calculator;

    @BeforeEach
    void setUp() {
        BaseCuotaProperties cuotaProperties = mock(BaseCuotaProperties.class);
        when(cuotaProperties.getBaseMeses()).thenReturn(12); // valor configurable
        calculator = new BaseCuotaCalculatorImpl(cuotaProperties);
    }

    @Test
    void testBaseCuotaExactly12Months() {
        Double result = calculator.getBaseCuota(12);
        assertEquals(1.0, result, 0.0001);
    }

    @Test
    void testBaseCuotaLessThan12Months() {
        Double result = calculator.getBaseCuota(6);
        assertEquals(1.6, result, 0.0001);
    }

    @Test
    void testBaseCuotaMoreThan12Months() {
        Double result = calculator.getBaseCuota(15);
        assertEquals(0.91, result, 0.0001);
    }

    @Test
    void testBaseCuotaMaxReductionLimit() {
        Double result = calculator.getBaseCuota(20);
        assertEquals(0.8, result, 0.0001);
    }

    @Test
    void testBaseCuotaEdgeCaseOneMonth() {
        Double result = calculator.getBaseCuota(1);
        assertEquals(2.1, result, 0.0001);
    }
}
