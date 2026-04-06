package com.example.sistema_inventario_back.repository.tela;

import com.example.sistema_inventario_back.entity.tela.ImagenMateriaPrima;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImagenMateriaPrimaRepository extends JpaRepository<ImagenMateriaPrima, Integer> {

}