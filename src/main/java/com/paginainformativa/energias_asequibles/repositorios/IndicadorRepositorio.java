package com.paginainformativa.energias_asequibles.repositorios;

import com.paginainformativa.energias_asequibles.modelos.Indicador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IndicadorRepositorio extends JpaRepository<Indicador, Long> {

    public Optional<Indicador> findByProyectoId(Long proyectoId);

}
