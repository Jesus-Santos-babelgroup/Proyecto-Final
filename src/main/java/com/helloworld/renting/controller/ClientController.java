package com.helloworld.renting.controller;

import com.helloworld.renting.dto.ClientDto;
import com.helloworld.renting.service.client.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clients")
@Tag(name = "clients", description = "Operaciones sobre clientes")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/hello")
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok("¡La app está viva, tronco!");
    }

    @PostMapping("")
    @Operation(
            summary = "Crear un cliente",
            description = "Crea un nuevo cliente potencial",
            tags = {"clients"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Cliente creado exitosamente"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Datos inválidos o incompletos"
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Ya existe un cliente con el mismo NIF o email"
            )
    })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Datos del nuevo cliente",
            required = true,
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ClientDto.class),
                    examples = {
                            @ExampleObject(
                                    name = "clienteNuevo",
                                    summary = "Ejemplo de cliente nuevo",
                                    value = "{\n" +
                                            "  \"name\": \"Juan\",\n" +
                                            "  \"firstSurname\": \"García\",\n" +
                                            "  \"secondSurname\": \"López\",\n" +
                                            "  \"addressId\": 1,\n" +
                                            "  \"countryId\": 1,\n" +
                                            "  \"phone\": \"666555444\",\n" +
                                            "  \"nif\": \"12345678A\",\n" +
                                            "  \"dateOfBirth\": \"1990-01-15\",\n" +
                                            "  \"email\": \"juan@example.com\",\n" +
                                            "  \"scoring\": 80\n" +
                                            "}"
                            )
                    }
            )
    )
    public ResponseEntity<ClientDto> createClient(@Valid @RequestBody ClientDto clientDto) {
        ClientDto createdClient = clientService.createClient(clientDto);
        return new ResponseEntity<>(createdClient, HttpStatus.CREATED);
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
            description = "Elimina un cliente si no tiene solicitudes asociadas",
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
