package com.helloworld.renting.controller;

import com.helloworld.renting.service.economicdata.EconomicDataService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/economic-data")
@Tag(name = "Economic data", description = "Operations with clients economic data")
public class EconomicDataController {

    private EconomicDataService economicDataService;

    public EconomicDataController(EconomicDataService economicDataService) {
        this.economicDataService = economicDataService;
    }

    @DeleteMapping("/clients/{id}")
    @Operation(
            summary = "Deletes a client's economic data",
            description = "Deletes a client's economic data if it exists given the client's ID",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Economic data deleted"),
                    @ApiResponse(responseCode = "404", description = "No economic data was found for this client")
            }
    )
    public ResponseEntity<Void> deleteClient(@PathVariable Long id) {
        return null;
    }

}
