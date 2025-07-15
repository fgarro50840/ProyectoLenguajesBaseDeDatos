package com.paginainformativa.energias_asequibles.repositorios_pl;

import com.paginainformativa.energias_asequibles.modelos.Reporte;
import com.paginainformativa.energias_asequibles.modelos.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RolRepositorioPl extends JpaRepository<Rol, Long> {

    @Query(value = "SELECT * FROM vista_roles", nativeQuery = true)
    List<Rol> listar();

    @Procedure(value = "crear_rol")
    Rol crearRole(@Param("rol_tipo") String rolTipo);

    @Procedure(value = "por_id")
    Rol porId(@Param("id_param") Long id);
}
