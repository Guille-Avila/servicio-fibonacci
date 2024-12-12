package com.prueba.proteccion.controllers;

import com.prueba.proteccion.dto.LoginRequestDTO;
import com.prueba.proteccion.dto.RegisterRequestDTO;
import com.prueba.proteccion.dto.TokenResponseDTO;
import com.prueba.proteccion.entities.UserEntity;
import com.prueba.proteccion.services.AuthService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.links.Link;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Autenticacion API", description = "API para gestionar la autenticacion de usuarios generando y validando tokens")
public class AuthController {

    private final AuthService authService;

    @Operation(
            summary = "Registrar usuarios",
            description = "Registra usuarios en el sistema",
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserEntity.class)),
                    links = @Link(name = "link.com.prueba") ),
                    @ApiResponse(description = "Error", responseCode = "404"),
                    @ApiResponse(responseCode = "500", description = "Error interno del servidor",
                            content = @Content(examples = @ExampleObject(value = "{\"mensaje\": \"No se ha podido procesar su solicitud, contacte a soporte\"}")))
            }
    )
//    @Hidden
    @PostMapping("/register")
    public ResponseEntity<TokenResponseDTO> register(@RequestBody final RegisterRequestDTO request) {
        final TokenResponseDTO token = authService.register(request);
        return ResponseEntity.ok(token);
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponseDTO>  login(@RequestBody final LoginRequestDTO request) {
        final TokenResponseDTO token = authService.login(request);
        return ResponseEntity.ok(token);
    }

    @PostMapping("/refresh")
    public TokenResponseDTO  refreshToken(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) {
        return authService.refreshToken(authHeader);
    }
}
