package org.example.laboratorio_1_kervin.services;

import org.example.laboratorio_1_kervin.dto.LoginRequestDTO;
import org.example.laboratorio_1_kervin.dto.LoginResponseDTO;
import org.example.laboratorio_1_kervin.model.Usuario;
import org.example.laboratorio_1_kervin.repository.UsuarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
public class AuthServices {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtEncoder jwtEncoder;

    public AuthServices(UsuarioRepository usuarioRepository,
                        PasswordEncoder passwordEncoder,
                        JwtEncoder jwtEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtEncoder = jwtEncoder;
    }

    public LoginResponseDTO login(LoginRequestDTO request) {

        if (request == null || request.username() == null
                || request.password() == null) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "Usuario o contraseña incorrectos"
            );
        }

        Usuario usuario = usuarioRepository
                .findByUsername(request.username())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.UNAUTHORIZED,
                        "Usuario o contraseña incorrectos"
                ));

        if (!passwordEncoder.matches(
                request.password(),
                usuario.getPassword())) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "Usuario o contraseña incorrectos"
            );
        }

        Instant ahora = Instant.now();
        long duracion = 3600;

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("laboratorio-api")
                .subject(usuario.getUsername())
                .issuedAt(ahora)
                .expiresAt(ahora.plus(duracion, ChronoUnit.SECONDS))
                .claim("rol", usuario.getRol().getNombre())
                .build();

        JwsHeader header = JwsHeader.with(MacAlgorithm.HS256).build();

        String token = jwtEncoder.encode(
                org.springframework.security.oauth2.jwt.JwtEncoderParameters
                        .from(header, claims)
        ).getTokenValue();

        return new LoginResponseDTO(
                token,
                null,
                "Bearer",
                duracion
        );
    }
}
