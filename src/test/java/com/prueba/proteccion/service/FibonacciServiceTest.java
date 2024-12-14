package com.prueba.proteccion.service;

import com.prueba.proteccion.entities.FibonacciEntity;
import com.prueba.proteccion.repositories.FibonacciRepository;
import com.prueba.proteccion.services.FibonacciService;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FibonacciServiceTest {

    @Mock
    private FibonacciRepository fibonacciRepository;

    @InjectMocks
    private FibonacciService fibonacciService;

    @DisplayName("Se obtiene secuencia fibonoacci a partir de una hora")
    @Test
    void testCalcularFibonacci() throws Exception {

        FibonacciEntity fibonacciEntity = new FibonacciEntity();
        fibonacciEntity.setFormatoHora("12:23:04");

//        FibonacciEntity savedEntity = new FibonacciEntity();
//        savedEntity.setId(1L);
//        savedEntity.setFormatoHora("12:23:04");
//        savedEntity.setSerie("[21, 13, 8, 5, 3, 2]");

//        when(fibonacciRepository.save(any(FibonacciEntity.class))).thenReturn(savedEntity);

        when(fibonacciRepository.save(any(FibonacciEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));
        FibonacciEntity result = fibonacciService.guardarFibonacci(fibonacciEntity);

        assertNotNull(result, "El resultado no debe ser nulo");
        assertEquals("12:23:04", result.getFormatoHora(), "El formato de hora debe mantenerse igual");

        // Se prueba metodo que genera secuencia
        List<Integer> serieEsperada = fibonacciService.generarFibonacci(2, 3, 6); // Semillas: 2, 3; segundos: 4 + 2
        assertEquals(serieEsperada.toString(), result.getSerie(), "La serie calculada debe ser la esperada");

        verify(fibonacciRepository).save(fibonacciEntity);
    }

    @Test
    void testGuardarFibonacciInvalidFormatShouldThrowException() {
        FibonacciEntity fibonacciEntity = new FibonacciEntity();
        fibonacciEntity.setFormatoHora("invalid_format");

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> fibonacciService.guardarFibonacci(fibonacciEntity)
        );

        assertEquals("El formato de hora debe ser HH:MM:SS.", exception.getMessage());

        verifyNoInteractions(fibonacciRepository);
    }

    @Test
    void testGuardarFibonacciNegativeMinutesShouldThrowException() {
        FibonacciEntity fibonacciEntity = new FibonacciEntity();
        fibonacciEntity.setFormatoHora("12:-34:56");

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> fibonacciService.guardarFibonacci(fibonacciEntity)
        );

        assertEquals("Los minutos no pueden ser negativos.", exception.getMessage());

        verifyNoInteractions(fibonacciRepository);
    }

    @Test
    void testGetFibonacci() {
        List<FibonacciEntity> listFibopnacci = fibonacciService.listarFibonaccis();
        assertNotNull(listFibopnacci);
        assertInstanceOf(List.class, listFibopnacci);

    }

}
