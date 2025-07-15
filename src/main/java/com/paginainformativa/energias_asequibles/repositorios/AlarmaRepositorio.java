package com.paginainformativa.energias_asequibles.repositorios;

import com.paginainformativa.energias_asequibles.modelos.Alarma;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlarmaRepositorio extends JpaRepository<Alarma, Long> {
}