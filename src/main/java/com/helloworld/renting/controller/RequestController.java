package com.helloworld.renting.controller;

import com.helloworld.renting.dto.RentingRequestDto;
import com.helloworld.renting.exceptions.notfound.NotFoundException;
import com.helloworld.renting.service.request.RequestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/requests")
@Tag(name = "requests", description = "Operaciones sobre solicitudes de renting")
public class RequestController {

    private final RequestService requestService;

    public RequestController(RequestService requestService) {
        this.requestService = requestService;
    }

    @PostMapping()
    @Operation(
            summary = "Crea una nueva solicitud de renting",
            description = "Calcula cuota y da un prerresultado automático de resolución",
            tags = {"requests"},
            responses = {
                    @ApiResponse(responseCode = "201", description = "Solicitud creada"),
                    @ApiResponse(responseCode = "400", description = "Solicitud con formato inválido"),
                    @ApiResponse(responseCode = "500", description = "Error interno del servidor")
            }
    )
    public ResponseEntity<RentingRequestDto> createRequest(@Valid @RequestBody RentingRequestDto requestDto) {
        try {
            RentingRequestDto dto = requestService.create(requestDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(dto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Obtener solicitud",
            description = "Recupera los detalles de una solicitud por su ID",
            tags = {"requests"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Solicitud encontrada"),
                    @ApiResponse(responseCode = "404", description = "Solicitud no encontrada"),
                    @ApiResponse(responseCode = "500", description = "Error interno del servidor")
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
                    @ApiResponse(responseCode = "200", description = "Listado de solicitudes"),
                    @ApiResponse(responseCode = "500", description = "Error interno del servidor")
            }
    )
    public ResponseEntity<List<RentingRequestDto>> listRequests() {
        try {
            List<RentingRequestDto> listOfDtos = requestService.getAll();
            return ResponseEntity.status(HttpStatus.OK).body(listOfDtos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
