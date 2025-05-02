package com.helloworld.renting.controller;

import com.helloworld.renting.dto.RentingRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/requests")
@Tag(name = "requests", description = "Operaciones sobre solicitudes de renting")
public class RequestController {

    public RequestController() {
    }

    @PostMapping("")
    @Operation(
            summary = "Crear solicitud",
            description = "Crea una nueva solicitud de renting y calcula inversión/cuota",
            tags = {"requests"},
            responses = {
                    @ApiResponse(responseCode = "201", description = "Solicitud creada")
            }
    )
    public ResponseEntity<RentingRequestDto> createRequest(@Valid @RequestBody RentingRequestDto requestDto) {
        return null;
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Obtener solicitud",
            description = "Recupera los detalles de una solicitud por su ID",
            tags = {"requests"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Solicitud encontrada"),
                    @ApiResponse(responseCode = "404", description = "Solicitud no encontrada")
            }
    )
    public ResponseEntity<RentingRequestDto> getRequest(@PathVariable Long id) {
        return null;
    }

    @GetMapping("")
    @Operation(
            summary = "Listar solicitudes",
            description = "Obtiene un listado paginado de solicitudes, con filtros opcionales",
            tags = {"requests"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Listado de solicitudes")
            }
    )
    public ResponseEntity<List<RentingRequestDto>> listRequests() {
        return null;
    }
}
