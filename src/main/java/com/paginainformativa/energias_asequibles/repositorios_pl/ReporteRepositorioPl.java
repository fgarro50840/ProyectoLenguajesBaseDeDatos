package com.paginainformativa.energias_asequibles.repositorios_pl;

import com.paginainformativa.energias_asequibles.modelos.Proyecto;
import com.paginainformativa.energias_asequibles.modelos.Reporte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReporteRepositorioPl extends JpaRepository<Reporte, Long> {

    @Query(value = "SELECT * FROM vista_reportes", nativeQuery = true)
    List<Reporte> listar();

    @Procedure("guardar_reporte")
    Reporte save(
            @Param("p_id") Long id,
            @Param("p_proyecto_id") Long proyectoId,
            @Param("p_informacion") String informacion,
            @Param("p_activo") boolean activo
    );

    @Procedure("reporte_por_id")
    Reporte porId(@Param("p_id") Long id);

    @Procedure("reporte_por_proyecto")
    Reporte porProyecto(@Param("p_proyecto_id") Long proyectoId);
}
