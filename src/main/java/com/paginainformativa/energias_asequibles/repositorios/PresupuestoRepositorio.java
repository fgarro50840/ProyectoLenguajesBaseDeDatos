package com.paginainformativa.energias_asequibles.repositorios;

import com.paginainformativa.energias_asequibles.modelos.Presupuesto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PresupuestoRepositorio extends JpaRepository<Presupuesto, Long> {

    public Optional<Presupuesto> findByProyectoId(Long proyectoId);

}
