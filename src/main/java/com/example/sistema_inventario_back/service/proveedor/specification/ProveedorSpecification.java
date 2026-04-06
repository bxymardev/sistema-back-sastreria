package com.example.sistema_inventario_back.service.proveedor.specification;

import com.example.sistema_inventario_back.dto.proveedor.proveedor.ProveedorFiltroDTO;
import com.example.sistema_inventario_back.entity.proveedor.Proveedor;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.criteria.Predicate;

public class ProveedorSpecification {
    public static Specification<Proveedor> conFiltros(ProveedorFiltroDTO filtro){
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filtro.getNombreComercial() != null && !filtro.getNombreComercial().isBlank()){
                predicates.add(cb.like(cb.lower(root.get("nombreComercial")), "%" + filtro.getNombreComercial().toLowerCase() + "%"));
            }

            if (filtro.getIdentificacionFiscal() != null && !filtro.getIdentificacionFiscal().isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("identificacionFiscal")), "%" + filtro.getIdentificacionFiscal().toLowerCase() + "%"));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}