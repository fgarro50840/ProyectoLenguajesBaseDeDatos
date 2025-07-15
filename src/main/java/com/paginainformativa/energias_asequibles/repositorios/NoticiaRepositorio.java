package com.paginainformativa.energias_asequibles.repositorios;

import com.paginainformativa.energias_asequibles.modelos.Noticia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NoticiaRepositorio extends JpaRepository<Noticia, Long> {

    public Optional<Noticia> findByEnergiaId(Long energiaId);

}
