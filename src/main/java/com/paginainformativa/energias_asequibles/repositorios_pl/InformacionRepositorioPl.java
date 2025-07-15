package com.paginainformativa.energias_asequibles.repositorios_pl;

import com.paginainformativa.energias_asequibles.modelos.Alarma;
import com.paginainformativa.energias_asequibles.modelos.Informacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InformacionRepositorioPl extends JpaRepository<Informacion, Long> {

    @Query(value = "SELECT * FROM vista_informaciones", nativeQuery = true)
    List<Informacion> listar();

    @Procedure("guardar_informacion")
    Informacion save(
            @Param("p_id") Long id,
            @Param("p_energia_id") Long energiaId,
            @Param("p_info_texto") String infoTexto,
            @Param("p_activo") boolean activo
    );

    @Procedure("informacion_por_id")
    Informacion porId(@Param("p_id") Long id);

    @Procedure("informacion_por_energia")
    Informacion porEnergia(@Param("p_energia_id") Long energiaId);
}
