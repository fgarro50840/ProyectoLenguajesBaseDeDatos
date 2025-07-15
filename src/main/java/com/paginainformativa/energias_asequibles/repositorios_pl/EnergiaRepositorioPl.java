package com.paginainformativa.energias_asequibles.repositorios_pl;

import com.paginainformativa.energias_asequibles.modelos.Alarma;
import com.paginainformativa.energias_asequibles.modelos.Energia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnergiaRepositorioPl extends JpaRepository<Energia, Long> {

    @Query(value = "SELECT * FROM vista_energias", nativeQuery = true)
    List<Energia> listar();

    @Procedure("guardar_energia")
    Energia save(
            @Param("p_id") Long id,
            @Param("p_nombre") String nombre,
            @Param("p_descripcion") String descripcion,
            @Param("p_activo") boolean activo
    );

    @Procedure("energia_por_id")
    Energia porId(@Param("p_id") Long id);
}
