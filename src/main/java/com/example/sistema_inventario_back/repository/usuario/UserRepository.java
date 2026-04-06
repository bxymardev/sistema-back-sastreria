package com.example.sistema_inventario_back.repository.usuario;

import com.example.sistema_inventario_back.entity.usuario.Usuario;
import jakarta.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<Usuario, Integer> {
    @Transactional
    Optional<Usuario> findByNombreUsuario(String nombre_usuario);
}