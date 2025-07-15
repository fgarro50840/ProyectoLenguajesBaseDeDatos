package com.paginainformativa.energias_asequibles.services.implementations;

import com.paginainformativa.energias_asequibles.modelos.Energia;
import com.paginainformativa.energias_asequibles.modelos.Noticia;
import com.paginainformativa.energias_asequibles.modelos.Proyecto;
import com.paginainformativa.energias_asequibles.repositorios.EnergiaRepositorio;
import com.paginainformativa.energias_asequibles.repositorios_pl.EnergiaRepositorioPl;
import com.paginainformativa.energias_asequibles.services.excepciones.EntidadNoDesactivadaExcepcion;
import com.paginainformativa.energias_asequibles.services.excepciones.EntidadNoEncontradaExcepcion;
import com.paginainformativa.energias_asequibles.services.excepciones.EntidadNoGuardadaExcepcion;
import com.paginainformativa.energias_asequibles.services.interfaces.EnergiaService;
import com.paginainformativa.energias_asequibles.services.interfaces.NoticiaService;
import com.paginainformativa.energias_asequibles.services.interfaces.ProyectoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

@Service
public class EnergiaServiceImpl implements EnergiaService {

    private final EnergiaRepositorioPl eRepo;
    private final NoticiaService nService;
    private final ProyectoService pService;

    @Autowired
    public EnergiaServiceImpl(EnergiaRepositorioPl eRepo, NoticiaService nService, ProyectoService pService) {
        this.eRepo = eRepo;
        this.nService = nService;
        this.pService = pService;
    }

    @Override
    @Transactional
    public List<Energia> listar() {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            boolean esAdmin = auth != null && auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"));
            List<Energia> todas = eRepo.listar();
            return esAdmin ? todas : todas.stream().filter(Energia::isActivo).toList();
        } catch (Exception e) {
            throw new EntidadNoEncontradaExcepcion("No se pudieron listar las energías", e);
        }
    }

    @Override
    @Transactional
    public Energia save(Energia energia) {
        if (energia == null) {
            throw new EntidadNoGuardadaExcepcion("La energía no puede ser nula");
        }
        try {
            Long id = energia.getId() != null ? energia.getId() : 0L;
            boolean activo = energia.getId() == null ? true : energia.isActivo();
            return eRepo.save(
                    id,
                    energia.getNombre(),
                    energia.getDescripcion(),
                    activo
            );
        } catch (Exception e) {
            throw new EntidadNoGuardadaExcepcion("No se pudo guardar la energía", e);
        }
    }

    @Override
    @Transactional
    public Energia porId(Long id) {
        Energia energia = eRepo.porId(id);
        if (energia == null) {
            throw new EntidadNoEncontradaExcepcion("Energia con ID " + id + " no encontrada");
        }
        return energia;
    }

    @Transactional
    @Override
    public void desactivar(Long id) {
        try {
            Energia energia = porId(id);
            energia.setActivo(false);
            save(energia);

            try {
                Noticia noticia = nService.porEnergia(id);
                noticia.setActivo(false);
                nService.save(noticia);
            } catch (EntidadNoEncontradaExcepcion e) {}

            try {
                Proyecto proyecto = pService.porEnergia(id);
                proyecto.setActivo(false);
                pService.save(proyecto);
            } catch (EntidadNoEncontradaExcepcion e) {}

        } catch (Exception e) {
            throw new EntidadNoDesactivadaExcepcion("No se pudo desactivar la energía con ID " + id, e);
        }
    }

    @Transactional
    @Override
    public void reactivar(Long id) {
        try {
            Energia energia = porId(id);
            energia.setActivo(true);
            save(energia);

            try {
                Noticia noticia = nService.porEnergia(id);
                noticia.setActivo(true);
                nService.save(noticia);
            } catch (EntidadNoEncontradaExcepcion e) {}

            try {
                Proyecto proyecto = pService.porEnergia(id);
                proyecto.setActivo(true);
                pService.save(proyecto);
            } catch (EntidadNoEncontradaExcepcion e) {}

        } catch (Exception e) {
            throw new EntidadNoDesactivadaExcepcion("No se pudo reactivar la energía con ID " + id, e);
        }
    }
}
