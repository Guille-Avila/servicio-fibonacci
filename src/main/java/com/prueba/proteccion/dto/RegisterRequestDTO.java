package com.prueba.proteccion.dto;

import lombok.Data;

@Data
public class RegisterRequestDTO {
    String email;
    String password;
    String username;
}

//public record RegisterRequestDTO (
//        String email,
//        String password,
//        String username
//) {}