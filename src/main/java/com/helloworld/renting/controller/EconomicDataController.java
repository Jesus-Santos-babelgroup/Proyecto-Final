package com.helloworld.renting.controller;

import com.helloworld.renting.dto.EconomicDataEmployedDto;
import com.helloworld.renting.dto.EconomicDataSelfEmployedDto;
import com.helloworld.renting.service.client.EconomicDataService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/clients")
@Tag(name = "clients_economic_data", description = "Operaciones sobre la información bancaria de los clientes")
public class EconomicDataController{

    private final EconomicDataService economicDataService;

    public EconomicDataController(EconomicDataService economicDataService){
        this.economicDataService = economicDataService;
    }

    @PostMapping("/{clientId}/economic_data/self_employed")
    @Operation(
            summary = "Añadir ingresos del cliente",
            description = "Añadir ingresos autónomos de un cliente",
            tags = {"clients_economic_data"},
            responses = {
                    @ApiResponse(responseCode = "201", description = "Datos económicos creados correctamente"),
                    @ApiResponse(responseCode = "400", description = "Solicitud incorrecta: cliente no existe o datos inválidos"),
                    @ApiResponse(responseCode = "409", description = "Ya existen datos para ese cliente en el año especificado"),
                    @ApiResponse(responseCode = "500", description = "Error interno del servidor")
            }

    )
    public ResponseEntity<EconomicDataSelfEmployedDto> addEconomicalDataSelfEmployee(
            @Valid @RequestBody EconomicDataSelfEmployedDto economicDataSelfEmployedDto,
            @PathVariable Long clientId) {

        EconomicDataSelfEmployedDto economicDataCreated = economicDataService.addEconomicDataSelfEmployed(
                economicDataSelfEmployedDto,
                clientId);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(economicDataCreated);
    }

    @PostMapping("/{clientId}/economic_data/employed")
    @Operation(
            summary = "Añadir ingresos de un cliente",
            description = "Añadir ingresos de tipo asalariado de un cliente",
            tags = {"clients_economic_data"},
            responses = {
                    @ApiResponse(responseCode = "201", description = "Datos económicos creados correctamente"),
                    @ApiResponse(responseCode = "400", description = "Solicitud incorrecta: fechas inválidas o cliente no existe"),
                    @ApiResponse(responseCode = "409", description = "Ya existen datos para ese cliente en el año especificado"),
                    @ApiResponse(responseCode = "500", description = "Error interno del servidor")
            }
    )
    public ResponseEntity<EconomicDataEmployedDto> addEconomicalDataEmployee(
            @Valid @RequestBody EconomicDataEmployedDto economicDataEmployedDto,
            @PathVariable Long clientId) {
        EconomicDataEmployedDto economicDataCreated = economicDataService.addEconomicDataEmployed(
                economicDataEmployedDto,
                clientId);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(economicDataCreated);
    }


}
