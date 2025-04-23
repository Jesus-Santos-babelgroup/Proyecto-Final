package com.helloworld.renting.controller;

import com.helloworld.renting.service.PruebaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PruebaControllerTest {

    @Mock
    PruebaService pruebaService;

    PruebaController sut;

    @BeforeEach
    void setUp() {
        sut = new PruebaController(pruebaService);
    }

    @Test
    void should_returnMessage_when_pruebaExitosa() {
        // Given
        String message = "Hola, MyBatis desde MariaDB";
        when(pruebaService.obtenerMensaje()).thenReturn(message);

        // When
        String actualMessage = sut.prueba();

        // Then
        assertEquals(message, actualMessage);
    }

    @Test
    void should_raiseException_when_pruebaFallida() {
        // Given
        String message = "Error al ejecutar la prueba";
        when(pruebaService.obtenerMensaje()).thenThrow(new RuntimeException("BOOM"));

        // When
        RuntimeException exception = assertThrows(RuntimeException.class, () -> sut.prueba());

        // Then
        assertEquals(message, exception.getMessage());
    }

}