package com.prueba.proteccion.services;

import com.prueba.proteccion.dto.LoginRequestDTO;
import com.prueba.proteccion.dto.RegisterRequestDTO;
import com.prueba.proteccion.dto.TokenResponseDTO;
import com.prueba.proteccion.entities.TokenEntity;
import com.prueba.proteccion.entities.UserEntity;
import com.prueba.proteccion.repositories.TokenRepository;
import com.prueba.proteccion.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final TokenRepository tokenRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public TokenResponseDTO register(RegisterRequestDTO request) {
        UserEntity user = UserEntity.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        UserEntity savedUser = userRepository.save(user);
        String jwtToken = jwtService.generateToken(user);
        var refreshjwtToken = jwtService.generateRefreshToken(user);
        saveUserToken(savedUser, jwtToken);

        return new TokenResponseDTO(jwtToken, refreshjwtToken);
    }

    private void saveUserToken(UserEntity user, String jwtToken) {
        TokenEntity token = TokenEntity.builder()
                .idUsuario(user.getId())
                .token(jwtToken)
                .tokenType(TokenEntity.TokenType.BEARER)
                .expired(Boolean.FALSE)
                .revoked(Boolean.FALSE)
                .build();
        tokenRepository.save(token);
    }

    public TokenResponseDTO login(LoginRequestDTO request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        UserEntity user = userRepository.findByUsername(request.getUsername())
                .orElseThrow();
        String jwtToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
        return new TokenResponseDTO(jwtToken, refreshToken);
    }

    public TokenResponseDTO refreshToken(String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Invalid Bearer Token");
        }

        String refreshToken = authHeader.substring(7);
        String userName = jwtService.extractUsername(refreshToken);

        if (userName == null) {
            throw new IllegalArgumentException("Invalid Refresh Token");
        }

        UserEntity user = userRepository.findByUsername(userName)
                .orElseThrow(() -> new UsernameNotFoundException(userName));

        if (!jwtService.isTokenValid(refreshToken, user)) {
            throw new IllegalArgumentException("Invalid Refresh Token");
        }

        String accessToken = jwtService.generateToken(user);
        String refreshTokenUpdated = jwtService.generateRefreshToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, accessToken);
        return new TokenResponseDTO(accessToken, refreshTokenUpdated);
    }

    private void revokeAllUserTokens(UserEntity user) {
        List<TokenEntity> validUserTokens = tokenRepository
                .findAllValidIsFalseOrRevokedIsFalseByIdUsuario(user.getId());
        if(!validUserTokens.isEmpty()) {
            for (TokenEntity token: validUserTokens) {
                token.setExpired(Boolean.TRUE);
                token.setRevoked(Boolean.TRUE);
            }
            tokenRepository.saveAll(validUserTokens);
        }
    }


}
