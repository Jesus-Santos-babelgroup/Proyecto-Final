package com.helloworld.renting.controller;

import com.helloworld.renting.exceptions.RentingException;
import com.helloworld.renting.exceptions.notfound.ClientNotFoundException;
import com.helloworld.renting.exceptions.attributes.ClientWithPendingRequestsException;
import com.helloworld.renting.service.client.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
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
            @ApiResponse(responseCode = "409", description = "Cliente con solicitudes registradas (no se puede eliminar)")
    })
    public ResponseEntity<Void> deleteClient(@PathVariable Long id) {
        try {
            clientService.deleteClientById(id);
            return ResponseEntity.noContent().build();
        } catch (ClientNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (ClientWithPendingRequestsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @DeleteMapping
    @Operation(summary = "Eliminar un cliente por NIF", responses = {
            @ApiResponse(responseCode = "204", description = "Cliente eliminado correctamente"),
            @ApiResponse(responseCode = "400", description = "Formato de NIF inválido"),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado"),
            @ApiResponse(responseCode = "409", description = "Cliente con solicitudes registradas (no se puede eliminar)")
    })
    public ResponseEntity<Void> deleteClientByNif(@RequestParam String nif) {
        try {
            clientService.deleteClientByNif(nif);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (ClientNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (ClientWithPendingRequestsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }
}
