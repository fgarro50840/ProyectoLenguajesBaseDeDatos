package com.paginainformativa.energias_asequibles.repositorios;

import com.paginainformativa.energias_asequibles.modelos.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolRepositorio extends JpaRepository<Rol, Long> {
}
