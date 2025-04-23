package com.helloworld.renting.controller;

import com.helloworld.renting.service.PruebaService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PruebaController {
    private final PruebaService pruebaService;

    public PruebaController(PruebaService pruebaService) {
        this.pruebaService = pruebaService;
    }

    @GetMapping("/prueba")
    public String prueba() {
        try {
            return pruebaService.obtenerMensaje();
        } catch (Exception e) {
            throw new RuntimeException("Error al ejecutar la prueba", e);
        }
    }
}