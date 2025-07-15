package com.paginainformativa.energias_asequibles.repositorios;

import com.paginainformativa.energias_asequibles.modelos.Energia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnergiaRepositorio extends JpaRepository<Energia, Long> {
}
