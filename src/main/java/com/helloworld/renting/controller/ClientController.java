package com.helloworld.renting.controller;

import com.helloworld.renting.service.client.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/clients")
@Tag(name = "clients", description = "Operaciones sobre clientes")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un cliente por ID", responses = {
            @ApiResponse(responseCode = "204", description = "Cliente eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado"),
            @ApiResponse(responseCode = "409", description = "Cliente con solicitudes pendientes")
    })
    public ResponseEntity<Void> deleteClient(@PathVariable Long id) {
        clientService.deleteClientById(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    @Operation(summary = "Eliminar un cliente por NIF", responses = {
            @ApiResponse(responseCode = "204", description = "Cliente eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado"),
            @ApiResponse(responseCode = "409", description = "Cliente con solicitudes pendientes")
    })
    public ResponseEntity<Void> deleteClientByNif(@RequestParam String nif) {
        clientService.deleteClientByNif(nif);
        return ResponseEntity.noContent().build();
    }
}
