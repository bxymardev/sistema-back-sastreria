package com.example.sistema_inventario_back.service.config_service;

import com.example.sistema_inventario_back.entity.usuario.AuthenticationRequest;
import com.example.sistema_inventario_back.entity.usuario.AuthenticationResponse;
import com.example.sistema_inventario_back.entity.usuario.RegisterRequest;

public interface AuthService {
    AuthenticationResponse register (RegisterRequest request);
    AuthenticationResponse authenticate (AuthenticationRequest request);
}
