package com.paginainformativa.energias_asequibles.services.implementations;

import com.paginainformativa.energias_asequibles.modelos.Proyecto;
import com.paginainformativa.energias_asequibles.repositorios.ProyectoRepositorio;
import com.paginainformativa.energias_asequibles.repositorios_pl.ProyectoRepositorioPl;
import com.paginainformativa.energias_asequibles.services.excepciones.EntidadNoDesactivadaExcepcion;
import com.paginainformativa.energias_asequibles.services.excepciones.EntidadNoEncontradaExcepcion;
import com.paginainformativa.energias_asequibles.services.excepciones.EntidadNoGuardadaExcepcion;
import com.paginainformativa.energias_asequibles.services.interfaces.ProyectoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

@Service
public class ProyectoServiceImpl implements ProyectoService {

    private final ProyectoRepositorioPl pRepo;

    @Autowired
    public ProyectoServiceImpl(ProyectoRepositorioPl pRepo) {
        this.pRepo = pRepo;
    }

    @Override
    @Transactional
    public List<Proyecto> listar() {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            boolean esAdmin = auth != null && auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"));
            List<Proyecto> todos = pRepo.listar();
            return esAdmin ? todos : todos.stream().filter(Proyecto::isActivo).toList();
        } catch (Exception e) {
            throw new EntidadNoEncontradaExcepcion("No se pudieron listar los proyectos", e);
        }
    }

    @Override
    @Transactional
    public Proyecto save(Proyecto proyecto) {
        if (proyecto == null) {
            throw new EntidadNoGuardadaExcepcion("El proyecto no puede ser nulo");
        }
        try {
            Long id = proyecto.getId() != null ? proyecto.getId() : 0L;
            boolean activo = proyecto.getId() == null ? true : proyecto.isActivo();

            return pRepo.save(
                    id,
                    proyecto.getEnergia().getId(),
                    proyecto.getNombre(),
                    proyecto.getDescripcion(),
                    activo
            );
        } catch (Exception e) {
            throw new EntidadNoGuardadaExcepcion("No se pudo guardar el proyecto", e);
        }
    }

    @Override
    @Transactional
    public Proyecto porId(Long id) {
        Proyecto proyecto = pRepo.porId(id);
        if (proyecto == null) {
            throw new EntidadNoEncontradaExcepcion("Proyecto con ID " + id + " no encontrado");
        }
        return proyecto;
    }

    @Override
    public Proyecto porEnergia(Long energiaId) {
        Proyecto proyecto = pRepo.porEnergia(energiaId);
        if (proyecto == null) {
            throw new EntidadNoEncontradaExcepcion("Proyecto con ID " + energiaId + " no encontrado");
        }
        return proyecto;
    }


    @Transactional
    @Override
    public void desactivar(Long id) {
        try {
            Proyecto proyecto = porId(id);

            proyecto.setActivo(false);

            save(proyecto);

        } catch (Exception e) {

            throw new EntidadNoDesactivadaExcepcion("No se pudo desactivar el proyecto con ID " + id, e);
        }
    }

    @Transactional
    @Override
    public void reactivar(Long id) {
        try {
            Proyecto proyecto = porId(id);

            proyecto.setActivo(true);

            save(proyecto);

        } catch (Exception e) {

            throw new EntidadNoDesactivadaExcepcion("No se pudo desactivar el proyecto con ID " + id, e);
        }
    }
}
