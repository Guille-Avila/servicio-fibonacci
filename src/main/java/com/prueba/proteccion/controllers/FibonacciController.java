package com.prueba.proteccion.controllers;

import com.prueba.proteccion.entities.FibonacciEntity;
import com.prueba.proteccion.services.FibonacciService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/fibonacci")
@AllArgsConstructor
@Tag(name = "Fibonacci API", description = "API para gestionar secuencias de Fibonacci")
public class FibonacciController {

    private final FibonacciService fibonacciService;

    @Operation(
            summary = "Obtener todos los Fibonacci",
            description = "Recupera una lista de todos los números de Fibonacci almacenados en la base de datos",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de números Fibonacci recuperada con éxito"),
                    @ApiResponse(responseCode = "500", description = "Error interno del servidor")
            }
    )
    @GetMapping
    public ResponseEntity<List<FibonacciEntity>> obtenerFibonaccis() {
        return ResponseEntity.ok(fibonacciService.listarFibonaccis());
    }

    @Operation(
            summary = "Crear un nuevo número Fibonacci",
            description = "Guarda un número de Fibonacci en la base de datos en base a partir de una hora hh:mm:ss",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Información del número Fibonacci a guardar",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = FibonacciEntity.class),
                            examples = @ExampleObject(value = "{ \"formatoHora\": \"12:23:04\" }")
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Número Fibonacci creado con éxito"),
                    @ApiResponse(responseCode = "500", description = "Error interno del servidor")
            }
    )
    @PostMapping
    public ResponseEntity<FibonacciEntity> agregarFibonaci(
            @RequestBody FibonacciEntity fibonacci) {
        return ResponseEntity.status(HttpStatus.CREATED).body(fibonacciService.guardarFibonacci(fibonacci));
    }
}


