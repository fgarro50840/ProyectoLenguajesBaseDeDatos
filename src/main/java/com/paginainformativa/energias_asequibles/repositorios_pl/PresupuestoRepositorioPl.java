package com.paginainformativa.energias_asequibles.repositorios_pl;

import com.paginainformativa.energias_asequibles.modelos.Presupuesto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PresupuestoRepositorioPl extends JpaRepository<Presupuesto, Long> {

    @Query(value = "SELECT * FROM vista_presupuestos", nativeQuery = true)
    List<Presupuesto> listar();

    @Procedure("guardar_presupuesto")
    Presupuesto save(
            @Param("p_id") Long id,
            @Param("p_proyecto_id") Long proyectoId,
            @Param("p_valor") java.math.BigDecimal valor,
            @Param("p_activo") boolean activo
    );

    @Procedure("presupuesto_por_id")
    Presupuesto porId(@Param("p_id") Long id);

    @Procedure("presupuesto_por_proyecto")
    Presupuesto porProyecto(@Param("p_proyecto_id") Long proyectoId);

}
