package org.example.laboratorio_1_kervin.Controller;

import lombok.RequiredArgsConstructor;
import org.example.laboratorio_1_kervin.repository.UsuarioRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioRepository usuarioRepository;

    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_SUPER-ADMIN-ROLE')")
    public List<Map<String, Object>> listarUsuarios() {
        return usuarioRepository.findAll()
                .stream()
                .map(usuario -> Map.<String, Object>of(
                        "id", usuario.getId(),
                        "username", usuario.getUsername(),
                        "rol", usuario.getRol().getNombre()
                ))
                .toList();
    }
}
