package org.example.laboratorio_1_kervin.Controller;

import org.example.laboratorio_1_kervin.dto.LoginRequestDTO;
import org.example.laboratorio_1_kervin.dto.LoginResponseDTO;
import org.example.laboratorio_1_kervin.dto.RegisterRequestDTO;
import org.example.laboratorio_1_kervin.services.AuthServices;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthServices authServices;

    @PostMapping("/login")
    public LoginResponseDTO login(@RequestBody LoginRequestDTO request) {
        return authServices.login(request);
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void register(@RequestBody RegisterRequestDTO request) {
        authServices.registrar(request);
    }
}