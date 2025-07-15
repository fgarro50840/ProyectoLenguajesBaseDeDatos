package com.paginainformativa.energias_asequibles.repositorios;

import com.paginainformativa.energias_asequibles.modelos.Proyecto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProyectoRepositorio extends JpaRepository<Proyecto, Long> {

    public Optional<Proyecto> findByEnergiaId(Long energiaId);

}
