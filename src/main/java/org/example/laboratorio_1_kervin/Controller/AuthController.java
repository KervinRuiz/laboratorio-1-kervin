package org.example.laboratorio_1_kervin.Controller;

import org.example.laboratorio_1_kervin.dto.LoginRequestDTO;
import org.example.laboratorio_1_kervin.dto.LoginResponseDTO;
import org.example.laboratorio_1_kervin.services.AuthServices;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthServices authServices;

    @PostMapping("/login")
    public LoginResponseDTO login(@RequestBody LoginRequestDTO request) {
        return authServices.login(request);
    }
}
