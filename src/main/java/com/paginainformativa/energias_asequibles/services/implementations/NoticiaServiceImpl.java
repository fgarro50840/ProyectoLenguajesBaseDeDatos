package com.paginainformativa.energias_asequibles.services.implementations;

import com.paginainformativa.energias_asequibles.modelos.Noticia;
import com.paginainformativa.energias_asequibles.repositorios.NoticiaRepositorio;
import com.paginainformativa.energias_asequibles.repositorios_pl.NoticiaRepositorioPl;
import com.paginainformativa.energias_asequibles.services.excepciones.EntidadNoDesactivadaExcepcion;
import com.paginainformativa.energias_asequibles.services.excepciones.EntidadNoEncontradaExcepcion;
import com.paginainformativa.energias_asequibles.services.excepciones.EntidadNoGuardadaExcepcion;
import com.paginainformativa.energias_asequibles.services.interfaces.NoticiaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

@Service
public class NoticiaServiceImpl implements NoticiaService {

    private final NoticiaRepositorioPl nRepo;

    @Autowired
    public NoticiaServiceImpl(NoticiaRepositorioPl nRepo) {
        this.nRepo = nRepo;
    }

    @Override
    @Transactional
    public List<Noticia> listar() {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            boolean esAdmin = auth != null && auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"));
            List<Noticia> todas = nRepo.listar();
            return esAdmin ? todas : todas.stream().filter(Noticia::isActivo).toList();
        } catch (Exception e) {
            throw new EntidadNoEncontradaExcepcion("No se pudieron listar las noticias", e);
        }
    }

    @Override
    @Transactional
    public Noticia save(Noticia noticia) {
        if (noticia == null) {
            throw new EntidadNoGuardadaExcepcion("La noticia no puede ser nula");
        }
        try {
            Long id = noticia.getId() != null ? noticia.getId() : 0L;
            boolean activo = noticia.getId() == null ? true : noticia.isActivo();

            return nRepo.save(
                    id,
                    noticia.getEnergia().getId(),
                    noticia.getTitulo(),
                    noticia.getInformacion(),
                    activo
            );
        } catch (Exception e) {
            throw new EntidadNoGuardadaExcepcion("No se pudo guardar la noticia", e);
        }
    }

    @Override
    @Transactional
    public Noticia porId(Long id) {
        Noticia noticia = nRepo.porId(id);
        if (noticia == null) {
            throw new EntidadNoEncontradaExcepcion("Noticia con ID " + id + " no encontrada");
        }
        return noticia;
    }

    @Override
    @Transactional
    public Noticia porEnergia(Long energiaId) {
        Noticia noticia = nRepo.porEnergia(energiaId);
        if (noticia == null) {
            throw new EntidadNoEncontradaExcepcion("Noticia con energía ID " + energiaId + " no encontrada");
        }
        return noticia;
    }

    @Transactional
    @Override
    public void desactivar(Long id) {
        try {
            Noticia noticia = porId(id);

            noticia.setActivo(false);

            save(noticia);

        } catch (Exception e) {

            throw new EntidadNoDesactivadaExcepcion("No se pudo desactivar la noticia con ID " + id, e);
        }
    }

    @Transactional
    @Override
    public void reactivar(Long id) {
        try {
            Noticia noticia = porId(id);

            noticia.setActivo(true);

            save(noticia);

        } catch (Exception e) {

            throw new EntidadNoDesactivadaExcepcion("No se pudo desactivar la noticia con ID " + id, e);
        }
    }
}
