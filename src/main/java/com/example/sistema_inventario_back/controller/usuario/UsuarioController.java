package com.example.sistema_inventario_back.controller.usuario;

import com.example.sistema_inventario_back.dto.usuario.*;
import com.example.sistema_inventario_back.entity.usuario.Usuario;
import com.example.sistema_inventario_back.service.config_service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {
    private final UsuarioService usuarioService;

    // Controlador para buscar un usuario mediante su id
    @GetMapping("/buscarUsuario/{id}")
    public ResponseEntity<Usuario> getUsuarioById(@PathVariable Integer id){
        Usuario usuario = usuarioService.getUsuarioById(id);
        return ResponseEntity.ok(usuario);
    }

    // Controlador para listar a todos los usuarios
    @GetMapping("/listarUsuarios")
    public ResponseEntity<Iterable<Usuario>> listarUsuarios(){
        return ResponseEntity.ok(usuarioService.listarUsuarios());
    }

    // Controlador para actualizar un usuario por id
    @PutMapping("/actualizarUsuario/{id}")
    public ResponseEntity<Usuario> actualizarUsuario(
            @PathVariable Integer id,
            @RequestBody UsuarioUpdateDTO dto){

        return ResponseEntity.ok(usuarioService.updateUsuario(id, dto));
    }

    // Controlador para actualizar el nombre de usuario
    @PutMapping("/updateNombreUsuario/{id}")
    public ResponseEntity<JwtActualizacionResponse> cambiarNombreUsuario(
            @PathVariable Integer id,
            @RequestBody @Valid UsuarioUpdateNombreDTO dto,
            Authentication authentication
            ){
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return ResponseEntity.ok(
                usuarioService.actualizarNombreUsuario(id, dto.getNuevoNombreUsuario(), userDetails)
        );
    }

    // Controlador para cambiar el nombre de usuario.
    @PutMapping("/updatePassword/{id}")
    public ResponseEntity<TokenResponse> cambiarContrasenia(
            @PathVariable Integer id,
            @RequestBody @Valid UsuarioUpdatePasswordDTO dto
            ){
        TokenResponse response = usuarioService.actualizarPassword(id, dto.getActualPassword(), dto.getNuevoPassword());
        return ResponseEntity.ok(response);
    }
}