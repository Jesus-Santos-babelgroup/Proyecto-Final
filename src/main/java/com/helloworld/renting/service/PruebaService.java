package com.helloworld.renting.service;

import com.helloworld.renting.mapper.PruebaMapper;
import org.springframework.stereotype.Service;

@Service
public class PruebaService {
    private final PruebaMapper pruebaMapper;

    public PruebaService(PruebaMapper pruebaMapper) {
        this.pruebaMapper = pruebaMapper;
    }

    public String obtenerMensaje() {
        return pruebaMapper.getPrueba();
    }
}