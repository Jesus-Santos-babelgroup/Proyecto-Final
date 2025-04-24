package com.helloworld.renting.controller;

import com.helloworld.renting.dto.ClientDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clients")
@Tag(name = "clients", description = "Operaciones sobre clientes")
public class ClientController {

    public ClientController() {
    }

    @GetMapping("/hello")
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok("¡La app está viva, tronco!");
    }

    @PostMapping("")
    @Operation(
            summary = "Crear un cliente",
            description = "Crea un nuevo cliente potencial",
            tags = {"clients"},
            responses = {
                    @ApiResponse(responseCode = "201", description = "Cliente creado")
            }
    )
    public ResponseEntity<ClientDto> createClient(@Valid @RequestBody ClientDto clientDto) {
        return null;
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Actualizar un cliente",
            description = "Actualiza los datos de un cliente existente",
            tags = {"clients"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Cliente actualizado"),
                    @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
            }
    )
    public ResponseEntity<ClientDto> updateClient(@PathVariable Long id,
                                                  @Valid @RequestBody ClientDto clientDto) {
        return null;
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Eliminar un cliente",
            description = "Elimina un cliente si no tiene solicitudes asociadas a través de su ID o el CIF del empleador",
            tags = {"clients"},
            responses = {
                    @ApiResponse(responseCode = "204", description = "Cliente eliminado"),
                    @ApiResponse(responseCode = "404", description = "Cliente no encontrado o con solicitudes")
            }
    )
    public ResponseEntity<Void> deleteClient(@PathVariable Long id) {
        return null;
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Obtener un cliente",
            description = "Recupera los datos de un cliente por su ID",
            tags = {"clients"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Cliente encontrado"),
                    @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
            }
    )
    public ResponseEntity<ClientDto> getClient(@PathVariable Long id) {
        return null;
    }

    @GetMapping("")
    @Operation(
            summary = "Listar clientes",
            description = "Obtiene un listado paginado de clientes",
            tags = {"clients"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Listado de clientes")
            }
    )
    public ResponseEntity<List<ClientDto>> listClients() {
        return null;
    }
}
