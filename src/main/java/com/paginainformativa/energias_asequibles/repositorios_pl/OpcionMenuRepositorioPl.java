package com.paginainformativa.energias_asequibles.repositorios_pl;

import com.paginainformativa.energias_asequibles.modelos.Noticia;
import com.paginainformativa.energias_asequibles.modelos.OpcionMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OpcionMenuRepositorioPl extends JpaRepository<OpcionMenu, Long> {

    @Query(value = "SELECT * FROM vista_opciones_menu", nativeQuery = true)
    List<OpcionMenu> listar();

    @Procedure("guardar_opcion_menu")
    OpcionMenu save(
            @Param("p_id") Long id,
            @Param("p_nombre") String nombre,
            @Param("p_activo") boolean activo
    );

    @Procedure("opcion_menu_por_id")
    OpcionMenu porId(@Param("p_id") Long id);

    @Procedure("listar_opciones_menu_activas")
    List<OpcionMenu> listarActivos();
}
