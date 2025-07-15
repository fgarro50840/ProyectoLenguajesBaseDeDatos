package com.paginainformativa.energias_asequibles.repositorios_pl;

import com.paginainformativa.energias_asequibles.modelos.Alarma;
import com.paginainformativa.energias_asequibles.modelos.Indicador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IndicadorRepositorioPl extends JpaRepository<Indicador, Long> {

    @Query(value = "SELECT * FROM vista_indicadores", nativeQuery = true)
    List<Indicador> listar();

    @Procedure("guardar_indicador")
    Indicador save(
            @Param("p_id") Long id,
            @Param("p_proyecto_id") Long proyectoId,
            @Param("p_rendimiento") int rendimiento,
            @Param("p_activo") boolean activo
    );

    @Procedure("indicador_por_id")
    Indicador porId(@Param("p_id") Long id);

    @Procedure("indicador_por_proyecto")
    Indicador porProyecto(@Param("p_proyecto_id") Long proyectoId);
}
