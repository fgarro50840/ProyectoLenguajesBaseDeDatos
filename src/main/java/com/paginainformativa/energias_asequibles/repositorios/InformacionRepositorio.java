package com.paginainformativa.energias_asequibles.repositorios;

import com.paginainformativa.energias_asequibles.modelos.Informacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InformacionRepositorio extends JpaRepository<Informacion, Long> {

    public Optional<Informacion> findByEnergiaId(Long energiaId);

}
