package com.prueba.proteccion.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class FibonacciEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String formatoHora;
    @Lob
    private String serie;

}
