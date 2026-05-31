package com.example.sistema_inventario_back.repository.cliente;

import com.example.sistema_inventario_back.dto.cliente.ClienteListarDTO;
import com.example.sistema_inventario_back.entity.cliente.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer>, JpaSpecificationExecutor<Cliente> {

    @Query("""
           SELECT new com.example.sistema_inventario_back.dto.cliente.ClienteListarDTO(
            c.idCliente,
            c.nombres,
            c.apellidoPaterno,
            c.apellidoMaterno,
            c.telefonoUno
           )
           FROM Cliente c
           WHERE c.estadoCliente = true
           ORDER BY c.fechaRegistro DESC
    """)
    Page<ClienteListarDTO> listarClientesActivos(Pageable pageable);
}