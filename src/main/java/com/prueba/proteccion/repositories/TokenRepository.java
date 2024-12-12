package com.prueba.proteccion.repositories;

import com.prueba.proteccion.entities.TokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<TokenEntity, Long> {
    List<TokenEntity> findAllValidIsFalseOrRevokedIsFalseByIdUsuario(Long userId);
    Optional<TokenEntity> findByToken(String token);
}
