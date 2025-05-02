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
            summary = "Crea una nueva solicitud de renting",
            description = "Calcula cuota y da un prerresultado automático de resolución",
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
    public ResponseEntity<List<RentingRequestDto>> getRequest(@PathVariable Long id) {
        if (id == null || id < 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        try {
            List<RentingRequestDto> dto = requestService.getByClientId(id);
            return ResponseEntity.status(HttpStatus.OK).body(dto);
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping()
    @Operation(
            summary = "Listar solicitudes",
            description = "Obtiene un listado de solicitudes",
            tags = {"requests"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Listado de solicitudes")
            }
    )
    public ResponseEntity<List<RentingRequestDto>> listRequests() {
        return null;
    }
}
