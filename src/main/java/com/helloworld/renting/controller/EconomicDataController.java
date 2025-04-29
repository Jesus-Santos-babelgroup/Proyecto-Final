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
@RequestMapping("/api/clients/")
@Tag(name = "clients_economic_data", description = "Operaciones sobre la información bancaria de los clientes")
public class EconomicDataController{

    private final EconomicDataService economicDataService;

    public EconomicDataController(EconomicDataService economicDataService){
        this.economicDataService = economicDataService;
    }

    @PostMapping("{clientId}/self_employed")
    @Operation(
            summary = "Añadir información bancaria a un cliente",
            description = "Crea un nuevo cliente potencial",
            tags = {"clients_economic_data"},
            responses = {
                    @ApiResponse(responseCode = "201", description = "Cliente creado")
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

    @PostMapping("{clientId}/employed")
    @Operation(
            summary = "Añadir información bancaria a un cliente",
            description = "Crea un nuevo cliente potencial",
            tags = {"clients_economic_data"},
            responses = {
                    @ApiResponse(responseCode = "201", description = "Cliente creado")
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
