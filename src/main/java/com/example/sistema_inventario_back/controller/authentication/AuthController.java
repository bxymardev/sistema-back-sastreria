package com.example.sistema_inventario_back.controller.authentication;

import com.example.sistema_inventario_back.entity.usuario.AuthenticationRequest;
import com.example.sistema_inventario_back.entity.usuario.AuthenticationResponse;
import com.example.sistema_inventario_back.entity.usuario.RegisterRequest;
import com.example.sistema_inventario_back.service.config_service.AuthService;
import com.example.sistema_inventario_back.service.config_service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> createUsuario(@RequestBody RegisterRequest request){
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/authentication")
    public ResponseEntity<AuthenticationResponse> authenticationResponse(@RequestBody AuthenticationRequest request){
        return ResponseEntity.ok(authService.authenticate(request));
    }
}
