package com.helloworld.renting.controller;

import com.helloworld.renting.dto.EconomicDataEmployedDto;
import com.helloworld.renting.dto.EconomicDataSelfEmployedDto;
import com.helloworld.renting.exceptions.db.DBException;
import com.helloworld.renting.exceptions.notfound.NotFoundException;
import com.helloworld.renting.service.economicData.EconomicDataService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/clients/")
@Tag(name = "clients_economic_data", description = "Operaciones sobre la información bancaria de los clientes")
public class EconomicDataController{

    private final com.helloworld.renting.service.economicData.EconomicDataService economicDataService;
    private Logger logger = LoggerFactory.getLogger(EconomicDataController.class);

    public EconomicDataController(EconomicDataService economicDataService){
        this.economicDataService = economicDataService;
    }

    @PostMapping("{id}/economic_data/self_employed")
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
            @PathVariable Long id) {

        EconomicDataSelfEmployedDto economicDataCreated = economicDataService.addEconomicDataSelfEmployed(
                economicDataSelfEmployedDto,
                id);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(economicDataCreated);
    }

    @PostMapping("{id}/economic_data/employed")
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
            @PathVariable Long id) {
        EconomicDataEmployedDto economicDataCreated = economicDataService.addEconomicDataEmployed(
                economicDataEmployedDto,
                id);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(economicDataCreated);
    }

    @DeleteMapping("{id}/economic-data/employed")
    @Operation(
            summary = "Deletes a client's economic data employed",
            description = "Deletes a client's economic data employed if it exists given the client's ID",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Economic data employed deleted"),
                    @ApiResponse(responseCode = "404", description = "No economic data employed was found for this client"),
                    @ApiResponse(responseCode = "500", description = "Failed to connect to the database")
            }
    )
    public ResponseEntity<Void> deleteEconomicDataEmployedFromClient(@PathVariable Long id) {
        try {
            logger.debug("Starting to delete economic data employed for client");
            economicDataService.deleteEconomicDataEmployedFromClient(id);
            return ResponseEntity.noContent().build();
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (DBException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("{id}/economic-data/self-employed")
    @Operation(
            summary = "Deletes a client's economic data self employed",
            description = "Deletes a client's economic data self employed if it exists given the client's ID",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Economic data self employed deleted"),
                    @ApiResponse(responseCode = "404", description = "No economic data self employed was found for this client"),
                    @ApiResponse(responseCode = "500", description = "Failed to connect to the database")
            }
    )
    public ResponseEntity<Void> deleteEconomicDataSelfEmployedFromClient(@PathVariable Long id) {
        try {
            logger.debug("Starting to delete economic data self employed for client");
            economicDataService.deleteEconomicDataSelfEmployedFromClient(id);
            return ResponseEntity.noContent().build();
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (DBException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

}
