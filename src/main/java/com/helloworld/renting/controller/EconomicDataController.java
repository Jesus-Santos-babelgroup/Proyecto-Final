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
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/clients/economic_data")
@Tag(name = "clients economic data", description = "Operaciones sobre la información bancaria de los clientes")
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

    @PutMapping("/self_employee/{id}")
    @Operation(
            summary = "Modificar datos económicos de cliente autónomo",
            description = "Permite modificar los datos de ingresos brutos, ingresos netos y año de entrada de un cliente autónomo. No permite modificar el ID del cliente ni el ID del registro.",
            tags = {"clients_economic_data"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Datos económicos de autónomo actualizados exitosamente"),
                    @ApiResponse(responseCode = "404", description = "Registro de datos económicos no encontrado")
            }
    )
    public ResponseEntity<EconomicDataSelfEmployedDto> editSelfEmployedData(@PathVariable Long id,
                                                                            @Valid @RequestBody EconomicDataSelfEmployedDto dto) {
        EconomicDataSelfEmployedDto updated = economicDataService.editSelfEmployedData(id, dto);
        return ResponseEntity.ok(updated);
    }

    @PutMapping("/employed/{id}")
    @Operation(
            summary = "Modificar datos económicos de cliente empleado",
            description = "Permite modificar los datos de ingresos brutos, ingresos netos, CIF de la empresa y fechas de inicio/fin de un cliente empleado. No permite modificar el ID del cliente ni el ID del registro.",
            tags = {"clients_economic_data"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Datos económicos de empleado actualizados exitosamente"),
                    @ApiResponse(responseCode = "404", description = "Registro de datos económicos no encontrado")
            }
    )
    public ResponseEntity<EconomicDataEmployedDto> editEmployedData(@PathVariable Long id,
                                                                    @Valid @RequestBody EconomicDataEmployedDto dto) {
        EconomicDataEmployedDto updated = economicDataService.editEmployedData(id, dto);
        return ResponseEntity.ok(updated);
    }



}
