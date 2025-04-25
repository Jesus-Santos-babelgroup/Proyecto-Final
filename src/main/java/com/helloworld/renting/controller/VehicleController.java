package com.helloworld.renting.controller;

import com.helloworld.renting.dto.VehicleDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/vehicles")
@Tag(name = "vehicles", description = "Operaciones sobre vehículos")
public class VehicleController {

    public VehicleController() {
    }

    @GetMapping("")
    @Operation(
            summary = "Listar vehículos",
            description = "Obtiene una lista de todos los vehículos disponibles",
            tags = {"vehicles"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de vehículos")
            }
    )
    public ResponseEntity<List<VehicleDto>> listVehicles() {
        return null;
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Obtener un vehículo",
            description = "Recupera los detalles de un vehículo por su ID",
            tags = {"vehicles"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Vehículo encontrado"),
                    @ApiResponse(responseCode = "404", description = "Vehículo no encontrado")
            }
    )
    public ResponseEntity<VehicleDto> getVehicle(@PathVariable Long id) {
        return null;
    }
}
