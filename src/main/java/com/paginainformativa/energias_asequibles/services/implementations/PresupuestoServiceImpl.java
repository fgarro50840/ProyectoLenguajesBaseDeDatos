package com.paginainformativa.energias_asequibles.services.implementations;

import com.paginainformativa.energias_asequibles.modelos.Presupuesto;
import com.paginainformativa.energias_asequibles.repositorios.PresupuestoRepositorio;
import com.paginainformativa.energias_asequibles.repositorios_pl.PresupuestoRepositorioPl;
import com.paginainformativa.energias_asequibles.services.excepciones.EntidadNoDesactivadaExcepcion;
import com.paginainformativa.energias_asequibles.services.excepciones.EntidadNoEncontradaExcepcion;
import com.paginainformativa.energias_asequibles.services.excepciones.EntidadNoGuardadaExcepcion;
import com.paginainformativa.energias_asequibles.services.interfaces.PresupuestoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

@Service
public class PresupuestoServiceImpl implements PresupuestoService {

    private final PresupuestoRepositorioPl pRepo;

    @Autowired
    public PresupuestoServiceImpl(PresupuestoRepositorioPl pRepo) {
        this.pRepo = pRepo;
    }

    @Override
    @Transactional
    public List<Presupuesto> listar() {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            boolean esAdmin = auth != null && auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"));
            List<Presupuesto> todos = pRepo.listar();
            return esAdmin ? todos : todos.stream().filter(Presupuesto::isActivo).toList();
        } catch (Exception e) {
            throw new EntidadNoEncontradaExcepcion("No se pudieron listar los presupuestos", e);
        }
    }

    @Override
    @Transactional
    public Presupuesto save(Presupuesto presupuesto) {
        if (presupuesto == null) {
            throw new EntidadNoGuardadaExcepcion("El presupuesto no puede ser nulo");
        }
        try {
            Long id = presupuesto.getId() != null ? presupuesto.getId() : 0L;
            boolean activo = presupuesto.getId() == null ? true : presupuesto.isActivo();

            return pRepo.save(
                    id,
                    presupuesto.getProyecto().getId(),
                    presupuesto.getValor(),
                    activo
            );
        } catch (Exception e) {
            throw new EntidadNoGuardadaExcepcion("No se pudo guardar el presupuesto", e);
        }
    }

    @Override
    @Transactional
    public Presupuesto porId(Long id) {
        Presupuesto p = pRepo.porId(id);
        if (p == null) {
            throw new EntidadNoEncontradaExcepcion("Presupuesto con ID " + id + " no encontrado");
        }
        return p;
    }

    @Override
    @Transactional
    public Presupuesto porProyecto(Long proyectoId) {
        Presupuesto p = pRepo.porProyecto(proyectoId);
        if (p == null) {
            throw new EntidadNoEncontradaExcepcion("Presupuesto con ID " + proyectoId + " no encontrado");
        }
        return p;
    }


    @Transactional
    @Override
    public void desactivar(Long id) {
        try {
            Presupuesto presupuesto = porId(id);
            presupuesto.setActivo(false);
            save(presupuesto);
        } catch (Exception e) {
            throw new EntidadNoDesactivadaExcepcion("No se pudo desactivar el presupuesto con ID " + id, e);
        }
    }

    @Transactional
    @Override
    public void reactivar(Long id) {
        try {
            Presupuesto presupuesto = porId(id);
            presupuesto.setActivo(true);
            save(presupuesto);
        } catch (Exception e) {
            throw new EntidadNoDesactivadaExcepcion("No se pudo desactivar el presupuesto con ID " + id, e);
        }
    }

}
