package com.example.sistema_inventario_back.service.config_service;

import com.example.sistema_inventario_back.dto.usuario.JwtActualizacionResponse;
import com.example.sistema_inventario_back.dto.usuario.TokenResponse;
import com.example.sistema_inventario_back.dto.usuario.UsuarioUpdateDTO;
import com.example.sistema_inventario_back.entity.usuario.Usuario;
import com.example.sistema_inventario_back.repository.usuario.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UsuarioService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public UsuarioService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    // Servicio para buscar un usuario por el ID
    public Usuario getUsuarioById(Integer id){
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + id));
    }

    // Servicio para listar a todos los usuarios del sistema.
    public Iterable<Usuario> listarUsuarios(){
        return userRepository.findAll();
    }

    // Servicio para actualizar campos del usuario
    public Usuario updateUsuario(Integer id, UsuarioUpdateDTO dto){
        Usuario usuario = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        usuario.setNombres(dto.getNombres());
        usuario.setApellidoPaterno(dto.getApellidoPaterno());
        usuario.setApellidoMaterno(dto.getApellidoMaterno());
        usuario.setCarnetIdentidad(dto.getCarnetIdentidad());

        return userRepository.save(usuario);
    }

    // Servicio para cambiar el nombre de usuario
    public JwtActualizacionResponse actualizarNombreUsuario(Integer id, String nuevoNombre, UserDetails userDetails){
        Usuario usuario = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        usuario.setNombreUsuario(nuevoNombre);
        userRepository.save(usuario);

        String token = jwtService.generateToken(userDetails);

        return new JwtActualizacionResponse(token, usuario.getNombreUsuario());
    }

    // Servicio para cambiar la contraseña
    public TokenResponse actualizarPassword(Integer id, String actualPassword, String nuevoPassword){
        Usuario usuario = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (!passwordEncoder.matches(actualPassword, usuario.getPassword())){
            throw new RuntimeException("Contraseña actual incorrecta");
        }

        usuario.setPassword(passwordEncoder.encode(nuevoPassword));
        userRepository.save(usuario);

        String newToken = jwtService.generateToken(usuario);

        return new TokenResponse(newToken, usuario);
    }
}
