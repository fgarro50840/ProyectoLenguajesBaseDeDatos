package com.paginainformativa.energias_asequibles.repositorios_pl;

import com.paginainformativa.energias_asequibles.modelos.OpcionMenu;
import com.paginainformativa.energias_asequibles.modelos.Proyecto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProyectoRepositorioPl extends JpaRepository<Proyecto, Long> {

    @Query(value = "SELECT * FROM vista_proyectos", nativeQuery = true)
    List<Proyecto> listar();


    @Procedure("guardar_proyecto")
    Proyecto save(
            @Param("p_id") Long id,
            @Param("p_energia_id") Long energiaId,
            @Param("p_nombre") String nombre,
            @Param("p_descripcion") String descripcion,
            @Param("p_activo") boolean activo
    );

    @Procedure("proyecto_por_id")
    Proyecto porId(@Param("p_id") Long id);

    @Procedure("proyecto_por_energia")
    Proyecto porEnergia(@Param("p_energia_id") Long energiaId);
}
