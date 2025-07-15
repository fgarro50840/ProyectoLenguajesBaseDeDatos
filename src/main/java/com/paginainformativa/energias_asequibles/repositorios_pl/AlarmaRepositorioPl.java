package com.paginainformativa.energias_asequibles.repositorios_pl;

import com.paginainformativa.energias_asequibles.modelos.Alarma;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlarmaRepositorioPl extends JpaRepository<Alarma, Long> {

    @Query(value = "SELECT * FROM vista_alarmas", nativeQuery = true)
    List<Alarma> listar();


    @Procedure(value = "guardar_alarma")
    Alarma save(
            @Param("p_id") Long id,
            @Param("p_nombre") String nombre,
            @Param("p_criticidad") String criticidad,
            @Param("p_descripcion") String descripcion
    );

    @Procedure(value = "alarma_por_id")
    Alarma porId(@Param("p_id") Long id);

    @Procedure(value = "eliminar_alarma")
    void eliminar(@Param("p_id") Long id);

}
