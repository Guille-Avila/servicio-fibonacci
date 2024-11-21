package com.prueba.proteccion.repositories;

import com.prueba.proteccion.entities.FibonacciEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FibonacciRepository extends JpaRepository<FibonacciEntity, Long> {
}
