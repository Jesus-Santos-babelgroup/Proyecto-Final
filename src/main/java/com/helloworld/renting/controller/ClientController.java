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
import com.helloworld.renting.exceptions.notfound.ClientNotFoundException;

import java.util.List;
import java.util.Map;

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
            description = "Crea un nuevo cliente potencial con su dirección y país",
            tags = {"clients"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Cliente creado exitosamente",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ClientDto.class),
                            examples = {
                                    @ExampleObject(
                                            name = "clienteCreado",
                                            summary = "Cliente creado correctamente",
                                            value = "{\n" +
                                                    "  \"id\": 1,\n" +
                                                    "  \"name\": \"Arturo\",\n" +
                                                    "  \"firstSurname\": \"Pérez\",\n" +
                                                    "  \"secondSurname\": \"Sanchís\",\n" +
                                                    "  \"phone\": \"667111389\",\n" +
                                                    "  \"nif\": \"11214139Z\",\n" +
                                                    "  \"dateOfBirth\": \"1951-10-25\",\n" +
                                                    "  \"email\": \"Arturo@ejemplo.com\",\n" +
                                                    "  \"scoring\": 44,\n" +
                                                    "  \"country\": {\n" +
                                                    "    \"isoA2\": \"ES\",\n" +
                                                    "    \"name\": \"España\"\n" +
                                                    "  },\n" +
                                                    "  \"address\": {\n" +
                                                    "    \"city\": \"Madrid\",\n" +
                                                    "    \"street\": \"Calle Gran Vía 111\",\n" +
                                                    "    \"zipCode\": \"28013\"\n" +
                                                    "  },\n" +
                                                    "  \"notificationAddress\": {\n" +
                                                    "    \"city\": \"Madrid\",\n" +
                                                    "    \"street\": \"Calle Gran Vía 111\",\n" +
                                                    "    \"zipCode\": \"28013\"\n" +
                                                    "  }\n" +
                                                    "}"
                                    )
                            }
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Datos inválidos o incompletos",
                    content = @Content(
                            mediaType = "application/json",
                            examples = {
                                    @ExampleObject(
                                            name = "errorValidacion",
                                            value = "{\n" +
                                                    "  \"timestamp\": \"2023-07-25T10:15:30.123+00:00\",\n" +
                                                    "  \"status\": 400,\n" +
                                                    "  \"error\": \"Bad Request\",\n" +
                                                    "  \"message\": \"La dirección no puede ser NULL\",\n" +
                                                    "  \"path\": \"/api/clients\"\n" +
                                                    "}"
                                    )
                            }
                    )
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Ya existe un cliente con el mismo NIF o email",
                    content = @Content(
                            mediaType = "application/json",
                            examples = {
                                    @ExampleObject(
                                            name = "clienteDuplicado",
                                            value = "{\n" +
                                                    "  \"timestamp\": \"2023-07-25T10:15:30.123+00:00\",\n" +
                                                    "  \"status\": 409,\n" +
                                                    "  \"error\": \"Conflict\",\n" +
                                                    "  \"message\": \"Ya existe un cliente con este NIF: 11214139Z\",\n" +
                                                    "  \"path\": \"/api/clients\"\n" +
                                                    "}"
                                    )
                            }
                    )
            )
    })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Datos del nuevo cliente con dirección y país",
            required = true,
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ClientDto.class),
                    examples = {
                            @ExampleObject(
                                    name = "clienteNuevo",
                                    summary = "Ejemplo de cliente nuevo",
                                    value = "{\n" +
                                            "  \"name\": \"Arturo\",\n" +
                                            "  \"firstSurname\": \"Pérez\",\n" +
                                            "  \"secondSurname\": \"Sanchís\",\n" +
                                            "  \"phone\": \"667111389\",\n" +
                                            "  \"email\": \"Arturo@ejemplo.com\",\n" +
                                            "  \"nif\": \"11214139Z\",\n" +
                                            "  \"dateOfBirth\": \"1951-10-25\",\n" +
                                            "  \"scoring\": 44,\n" +
                                            "  \"country\": {\n" +
                                            "    \"isoA2\": \"ES\"\n" +
                                            "  },\n" +
                                            "  \"address\": {\n" +
                                            "    \"city\": \"Madrid\",\n" +
                                            "    \"street\": \"Calle Gran Vía 111\",\n" +
                                            "    \"zipCode\": \"28013\"\n" +
                                            "  },\n" +
                                            "  \"notificationAddress\": {\n" +
                                            "    \"city\": \"Madrid\",\n" +
                                            "    \"street\": \"Calle Gran Vía 111\",\n" +
                                            "    \"zipCode\": \"28013\"\n" +
                                            "  }\n" +
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
            tags = {"clients"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Cliente actualizado exitosamente",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ClientDto.class),
                            examples = {
                                    @ExampleObject(
                                            name = "clienteActualizado",
                                            summary = "Cliente con datos actualizados",
                                            value = "{\n" +
                                                    "  \"id\": 1,\n" +
                                                    "  \"name\": \"Juan\",\n" +
                                                    "  \"firstSurname\": \"García\",\n" +
                                                    "  \"secondSurname\": \"López\",\n" +
                                                    "  \"addressId\": 1,\n" +
                                                    "  \"countryId\": 1,\n" +
                                                    "  \"phone\": \"666555444\",\n" +
                                                    "  \"nif\": \"12345678A\",\n" +
                                                    "  \"dateOfBirth\": \"1990-01-15\",\n" +
                                                    "  \"email\": \"juan.updated@example.com\",\n" +
                                                    "  \"scoring\": 85\n" +
                                                    "}"
                                    )
                            }
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Datos inválidos o incompletos",
                    content = @Content(
                            mediaType = "application/json",
                            examples = {
                                    @ExampleObject(
                                            name = "errorValidacion",
                                            value = "{\n" +
                                                    "  \"timestamp\": \"2025-05-02T12:00:00.000+02:00\",\n" +
                                                    "  \"status\": 400,\n" +
                                                    "  \"error\": \"Bad Request\",\n" +
                                                    "  \"message\": \"El email no tiene un formato válido\",\n" +
                                                    "  \"path\": \"/api/clients/1\"\n" +
                                                    "}"
                                    )
                            }
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Cliente no encontrado",
                    content = @Content(
                            mediaType = "application/json",
                            examples = {
                                    @ExampleObject(
                                            name = "clienteNoEncontrado",
                                            value = "{\n" +
                                                    "  \"timestamp\": \"2025-05-02T12:01:00.000+02:00\",\n" +
                                                    "  \"status\": 404,\n" +
                                                    "  \"error\": \"Not Found\",\n" +
                                                    "  \"message\": \"Cliente con ID 42 no encontrado\",\n" +
                                                    "  \"path\": \"/api/clients/42\"\n" +
                                                    "}"
                                    )
                            }
                    )
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Ya existe un cliente con el mismo NIF o email",
                    content = @Content(
                            mediaType = "application/json",
                            examples = {
                                    @ExampleObject(
                                            name = "conflictoDuplicado",
                                            value = "{\n" +
                                                    "  \"timestamp\": \"2025-05-02T12:02:00.000+02:00\",\n" +
                                                    "  \"status\": 409,\n" +
                                                    "  \"error\": \"Conflict\",\n" +
                                                    "  \"message\": \"Ya existe un cliente con este NIF: 12345678A\",\n" +
                                                    "  \"path\": \"/api/clients/1\"\n" +
                                                    "}"
                                    )
                            }
                    )
            )
    })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Datos para actualizar el cliente",
            required = true,
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ClientDto.class),
                    examples = {
                            @ExampleObject(
                                    name = "clienteParaActualizar",
                                    summary = "Ejemplo de datos a actualizar",
                                    value = "{\n" +
                                            "  \"name\": \"Juan\",\n" +
                                            "  \"firstSurname\": \"García\",\n" +
                                            "  \"secondSurname\": \"López\",\n" +
                                            "  \"addressId\": 1,\n" +
                                            "  \"countryId\": 1,\n" +
                                            "  \"phone\": \"666555999\",\n" +
                                            "  \"nif\": \"12345678A\",\n" +
                                            "  \"dateOfBirth\": \"1990-01-15\",\n" +
                                            "  \"email\": \"juan.updated@example.com\",\n" +
                                            "  \"scoring\": 85\n" +
                                            "}"
                            )
                    }
            )
    )

    public ResponseEntity<?> updateClient(@PathVariable Long id, @Valid @RequestBody ClientDto clientDto) {
        clientDto.setId(id);
        ClientDto updated = clientService.updateClient(clientDto);

        return new ResponseEntity<>(updated, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Eliminar un cliente",
            description = "Elimina un cliente si no tiene solicitudes asociadas",
            tags = {"clients"},
            responses = {
                    @ApiResponse(responseCode = "204", description = "Cliente eliminado"),
                    @ApiResponse(responseCode = "404", description = "Cliente no encontrado"),
                    @ApiResponse(responseCode = "409", description = "Cliente con solicitudes registradas")
            }
    )
    public ResponseEntity<Void> deleteClient(@PathVariable Long id) {
        try {
            clientService.deleteClientById(id);
            return ResponseEntity.noContent().build();
        } catch (ClientNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            // Manejo genérico de errores inesperados (por ejemplo, problemas de conexión)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
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
