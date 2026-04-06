package com.example.sistema_inventario_back.service.config_service;

import com.example.sistema_inventario_back.entity.Role;
import com.example.sistema_inventario_back.entity.usuario.AuthenticationRequest;
import com.example.sistema_inventario_back.entity.usuario.AuthenticationResponse;
import com.example.sistema_inventario_back.entity.usuario.RegisterRequest;
import com.example.sistema_inventario_back.entity.usuario.Usuario;
import com.example.sistema_inventario_back.repository.usuario.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor    
public class AuthServicelmpl implements AuthService{

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private final JwtService jwtService;

    @Autowired
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthenticationResponse register(RegisterRequest request){
        var usuario = Usuario.builder()
                .nombres(request.getNombres())
                .apellidoPaterno(request.getApellidoPaterno())
                .apellidoMaterno(request.getApellidoMaterno())
                .estado(request.getEstado())
                .nombreUsuario(request.getNombre_usuario())
                .carnetIdentidad(request.getCarnetIdentidad())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.ADMIN)
                .build();

        userRepository.save(usuario);

        var jwtToken = jwtService.generateToken(usuario);

        return AuthenticationResponse.builder()
                .token(jwtToken).build();
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getNombreUsuario(),
                        request.getPassword()
                )
        );

        var usuario = userRepository.findByNombreUsuario(request.getNombreUsuario())
                .orElseThrow(() -> new IllegalArgumentException("Usuario no registrado"));

        var jwtToken = jwtService.generateToken(usuario);

        Role role = usuario.getRole();

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .role(role)
                .build();
    }
}
