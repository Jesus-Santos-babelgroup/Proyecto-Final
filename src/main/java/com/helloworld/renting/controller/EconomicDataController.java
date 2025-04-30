package com.helloworld.renting.controller;

import com.helloworld.renting.service.economicdata.EconomicDataService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/clients")
@Tag(name = "Economic data", description = "Operations with clients economic data")
public class EconomicDataController {

    private EconomicDataService economicDataService;
    private Logger logger = LoggerFactory.getLogger(EconomicDataController.class);

    public EconomicDataController(EconomicDataService economicDataService) {
        this.economicDataService = economicDataService;
    }

    @DeleteMapping("/{id}/economic-data/employed")
    @Operation(
            summary = "Deletes a client's economic data employed",
            description = "Deletes a client's economic data employed if it exists given the client's ID",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Economic data employed deleted"),
                    @ApiResponse(responseCode = "404", description = "No economic data employed was found for this client")
            }
    )
    public ResponseEntity<Void> deleteEconomicDataEmployedFromClient(@PathVariable Long id) {
        try {
            logger.debug("Starting to delete economic data employed for client");
            economicDataService.deleteEconomicDataEmployedFromClient(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}/economic-data/employed")
    @Operation(
            summary = "Deletes a client's economic data self employed",
            description = "Deletes a client's economic data self employed if it exists given the client's ID",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Economic data self employed deleted"),
                    @ApiResponse(responseCode = "404", description = "No economic data self employed was found for this client")
            }
    )
    public ResponseEntity<Void> deleteEconomicDataSelfEmployedFromClient(@PathVariable Long id) {
        try {
            logger.debug("Starting to delete economic data self employed for client");
            economicDataService.deleteEconomicDataSelfEmployedFromClient(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

}
