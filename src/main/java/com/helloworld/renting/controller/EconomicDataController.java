package com.helloworld.renting.controller;

import com.helloworld.renting.dto.ClientDto;
import com.helloworld.renting.dto.EconomicDataEmployedDto;
import com.helloworld.renting.dto.EconomicDataSelfEmployedDto;
import com.helloworld.renting.service.client.EconomicDataService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/clients/economic_data")
@Tag(name = "clients_economic_data", description = "Operaciones sobre la información bancaria de los clientes")
public class EconomicDataController{

    private final EconomicDataService economicDataService;

    public EconomicDataController(EconomicDataService economicDataService){
        this.economicDataService = economicDataService;
    }

    @PostMapping("/self_employee")
    @Operation(
            summary = "Añadir información bancaria a un cliente",
            description = "Crea un nuevo cliente potencial",
            tags = {"clients_economic_data"},
            responses = {
                    @ApiResponse(responseCode = "201", description = "Cliente creado")
            }
    )
    public ResponseEntity<EconomicDataSelfEmployedDto> createClient(@Valid @RequestBody EconomicDataSelfEmployedDto economicDataSelfEmployedDto) {
        EconomicDataSelfEmployedDto economicDataCreated = economicDataService.addEconomicData(economicDataSelfEmployedDto);
        return null;
    }


}
