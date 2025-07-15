package com.paginainformativa.energias_asequibles.services.implementations;

import com.paginainformativa.energias_asequibles.modelos.Reporte;
import com.paginainformativa.energias_asequibles.repositorios.ReporteRepositorio;
import com.paginainformativa.energias_asequibles.repositorios_pl.ReporteRepositorioPl;
import com.paginainformativa.energias_asequibles.services.excepciones.EntidadNoDesactivadaExcepcion;
import com.paginainformativa.energias_asequibles.services.excepciones.EntidadNoEncontradaExcepcion;
import com.paginainformativa.energias_asequibles.services.excepciones.EntidadNoGuardadaExcepcion;
import com.paginainformativa.energias_asequibles.services.interfaces.ReporteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

@Service
public class ReporteServiceImpl implements ReporteService {

    private final ReporteRepositorioPl rRepo;

    @Autowired
    public ReporteServiceImpl(ReporteRepositorioPl rRepo) {
        this.rRepo = rRepo;
    }

    @Override
    @Transactional
    public List<Reporte> listar() {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            boolean esAdmin = auth != null && auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"));
            List<Reporte> todos = rRepo.listar();
            return esAdmin ? todos : todos.stream().filter(Reporte::isActivo).toList();
        } catch (Exception e) {
            throw new EntidadNoEncontradaExcepcion("No se pudieron listar los reportes", e);
        }
    }

    @Override
    @Transactional
    public Reporte save(Reporte reporte) {
        if (reporte == null) {
            throw new EntidadNoGuardadaExcepcion("El reporte no puede ser nulo");
        }
        try {
            Long id = reporte.getId() != null ? reporte.getId() : 0L;
            boolean activo = reporte.getId() == null ? true : reporte.isActivo();

            return rRepo.save(
                    id,
                    reporte.getProyecto().getId(),
                    reporte.getInformacion(),
                    activo
            );
        } catch (Exception e) {
            throw new EntidadNoGuardadaExcepcion("No se pudo guardar el reporte", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Reporte porId(Long id) {
        Reporte r = rRepo.porId(id);
        if (r == null) {
            throw new EntidadNoEncontradaExcepcion("Reporte con ID " + id + " no encontrado");
        }
        return r;
    }

    @Override
    public Reporte porProyecto(Long proyectoId) {
        Reporte r = rRepo.porProyecto(proyectoId);
        if (r == null) {
            throw new EntidadNoEncontradaExcepcion("Reporte con ID " + proyectoId + " no encontrado");
        }
        return r;
    }

    @Transactional
    @Override
    public void desactivar(Long id) {
        try {
            Reporte reporte = porId(id);
            reporte.setActivo(false);
            save(reporte);
        } catch (Exception e) {
            throw new EntidadNoDesactivadaExcepcion("No se pudo desactivar el reporte con ID " + id, e);
        }
    }

    @Transactional
    @Override
    public void reactivar(Long id) {
        try {
            Reporte reporte = porId(id);
            reporte.setActivo(true);
            save(reporte);
        } catch (Exception e) {
            throw new EntidadNoDesactivadaExcepcion("No se pudo reactivar el reporte con ID " + id, e);
        }
    }
}
