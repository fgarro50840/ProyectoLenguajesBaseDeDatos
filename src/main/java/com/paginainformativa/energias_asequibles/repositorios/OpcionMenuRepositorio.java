package com.paginainformativa.energias_asequibles.repositorios;

import com.paginainformativa.energias_asequibles.modelos.OpcionMenu;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OpcionMenuRepositorio extends JpaRepository<OpcionMenu, Long> {

    List<OpcionMenu> findByActivoTrue();
}
