package com.prueba.proteccion.controllers;

import com.prueba.proteccion.entities.FibonacciEntity;
import com.prueba.proteccion.services.FibonacciService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/fibonacci")
@AllArgsConstructor
public class FibonacciController {
    private final FibonacciService fibonacciService;


    @GetMapping
    public ResponseEntity<List<FibonacciEntity>> obtenerFibonaccis() {
        return ResponseEntity.ok(fibonacciService.listarFibonaccis());
    }

    @PostMapping
    public ResponseEntity<FibonacciEntity> agregarFibonaci(@RequestBody FibonacciEntity fibonacci) {
        return ResponseEntity.status(HttpStatus.CREATED).body(fibonacciService.guardarFibonacci(fibonacci));
    }

}

