package com.paginainformativa.energias_asequibles.repositorios_pl;

import com.paginainformativa.energias_asequibles.modelos.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepositorioPl extends JpaRepository<Usuario, Long> {

    @Query(value = "SELECT * FROM vista_usuarios", nativeQuery = true)
    List<Usuario> listar();

    @Procedure(value = "guardar_usuario")
    Usuario save(
            @Param("p_id") Long id,
            @Param("p_nombre") String nombre,
            @Param("p_contrasena") String contrasena,
            @Param("p_rol_id") Long rolId,
            @Param("p_activo") boolean activo
    );

    @Procedure(value = "usuario_por_id")
    Usuario porId(@Param("p_id") Long id);

    @Procedure(value = "usuario_por_nombre")
    Usuario porNombre(@Param("p_nombre") String nombre);

}
