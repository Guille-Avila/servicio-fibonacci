package com.prueba.proteccion.services;

import com.prueba.proteccion.entities.FibonacciEntity;
import com.prueba.proteccion.repositories.FibonacciRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@AllArgsConstructor
public class FibonacciService {
    private final FibonacciRepository productoRepository;

    public List<FibonacciEntity> listarFibonaccis() {
        return productoRepository.findAll();
    }

    public FibonacciEntity guardarFibonacci(FibonacciEntity fibonacci) {
        // Validar formato null
        if (fibonacci.getFormatoHora() == null || fibonacci.getFormatoHora().trim().isEmpty()) {
            throw new IllegalArgumentException("El formato de hora no puede estar vacoo.");
        }

        String[] partes = fibonacci.getFormatoHora().trim().split(":");

        // Validar formato tres partes
        if (partes.length != 3) {
            throw new IllegalArgumentException("El formato de hora debe ser HH:MM:SS.");
        }

        // Validar minutos segundos no negativos
        try {
            int minutos = Integer.parseInt(partes[1]);
            if (minutos < 0) {
                throw new IllegalArgumentException("Los minutos no pueden ser negativos.");
            }

            int segundos = Integer.parseInt(partes[2]);
            if (segundos < 0) {
                throw new IllegalArgumentException("Los segundos no pueden ser negativos.");
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Los minutos y segundos deben ser números válidos.");
        }

        List<Integer> semillas = new ArrayList<>();

        for (char digito : partes[1].toCharArray()) {
            semillas.add(Character.getNumericValue(digito));
        }

        Collections.sort(semillas);

        int segundos = Integer.parseInt(partes[2]) + 2;

        List<Integer> fibonacciSerie = generarFibonacci(semillas.get(0), semillas.get(1), segundos);

        fibonacci.setSerie(fibonacciSerie.toString());

        return productoRepository.save(fibonacci);
    }

    public List<Integer> generarFibonacci(int primero, int segundo, int cantidad) {
        List<Integer> serie = new ArrayList<>();
        serie.add(primero);
        serie.add(segundo);

        for (int i = 2; i < cantidad; i++) {
            int siguiente = serie.get(i - 1) + serie.get(i - 2);
            serie.add(siguiente);
        }

        serie.sort(Collections.reverseOrder());

        return serie;
    }

}

