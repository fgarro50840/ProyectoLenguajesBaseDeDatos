package com.paginainformativa.energias_asequibles.repositorios;

import com.paginainformativa.energias_asequibles.modelos.Reporte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReporteRepositorio extends JpaRepository<Reporte, Long> {

    public Optional<Reporte> findByProyectoId(Long proyectoId);

}
