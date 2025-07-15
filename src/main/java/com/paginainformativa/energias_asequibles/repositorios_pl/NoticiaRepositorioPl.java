package com.paginainformativa.energias_asequibles.repositorios_pl;

import com.paginainformativa.energias_asequibles.modelos.Noticia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoticiaRepositorioPl extends JpaRepository<Noticia, Long> {

    @Query(value = "SELECT * FROM vista_noticias", nativeQuery = true)
    List<Noticia> listar();

    @Procedure("guardar_noticia")
    Noticia save(
            @Param("p_id") Long id,
            @Param("p_energia_id") Long energiaId,
            @Param("p_titulo") String titulo,
            @Param("p_informacion") String informacion,
            @Param("p_activo") boolean activo
    );

    @Procedure("noticia_por_id")
    Noticia porId(@Param("p_id") Long id);

    @Procedure("noticia_por_energia")
    Noticia porEnergia(@Param("p_energia_id") Long energiaId);
}
