package com.paginainformativa.energias_asequibles.services.implementations;

import com.paginainformativa.energias_asequibles.modelos.Informacion;
import com.paginainformativa.energias_asequibles.repositorios.InformacionRepositorio;
import com.paginainformativa.energias_asequibles.repositorios_pl.InformacionRepositorioPl;
import com.paginainformativa.energias_asequibles.services.excepciones.EntidadNoDesactivadaExcepcion;
import com.paginainformativa.energias_asequibles.services.excepciones.EntidadNoEncontradaExcepcion;
import com.paginainformativa.energias_asequibles.services.excepciones.EntidadNoGuardadaExcepcion;
import com.paginainformativa.energias_asequibles.services.interfaces.InformacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

@Service
public class InformacionServiceImpl implements InformacionService {

    private final InformacionRepositorioPl iRepo;

    @Autowired
    public InformacionServiceImpl(InformacionRepositorioPl iRepo) {
        this.iRepo = iRepo;
    }

    @Override
    @Transactional
    public List<Informacion> listar() {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            boolean esAdmin = auth != null && auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"));
            List<Informacion> todas = iRepo.listar();
            return esAdmin ? todas : todas.stream().filter(Informacion::isActivo).toList();
        } catch (Exception e) {
            throw new EntidadNoEncontradaExcepcion("No se pudieron listar las informaciones", e);
        }
    }

    @Override
    @Transactional
    public Informacion save(Informacion informacion) {
        if (informacion == null) {
            throw new EntidadNoGuardadaExcepcion("La información no puede ser nula");
        }
        try {
            Long id = informacion.getId() != null ? informacion.getId() : 0L;
            boolean activo = informacion.getId() == null ? true : informacion.isActivo();
            return iRepo.save(
                    id,
                    informacion.getEnergia().getId(),
                    informacion.getInfoTexto(),
                    activo
            );
        } catch (Exception e) {
            throw new EntidadNoGuardadaExcepcion("No se pudo guardar la información", e);
        }
    }

    @Override
    @Transactional
    public Informacion porId(Long id) {
        Informacion info = iRepo.porId(id);
        if (info == null) {
            throw new EntidadNoEncontradaExcepcion("Información con ID " + id + " no encontrada");
        }
        return info;
    }

    @Override
    public Informacion porEnergia(Long energiaId) {
        Informacion info = iRepo.porEnergia(energiaId);
        if (info == null) {
            throw new EntidadNoEncontradaExcepcion("Información con energía ID " + energiaId + " no encontrada");
        }
        return info;
    }

    @Transactional
    @Override
    public void desactivar(Long id) {
        try {
            Informacion info = porId(id);
            info.setActivo(false);
            save(info);
        } catch (Exception e) {
            throw new EntidadNoDesactivadaExcepcion("No se pudo desactivar la información con ID " + id, e);
        }
    }

    @Transactional
    @Override
    public void reactivar(Long id) {
        try {
            Informacion info = porId(id);
            info.setActivo(true);
            save(info);
        } catch (Exception e) {
            throw new EntidadNoDesactivadaExcepcion("No se pudo reactivar la información con ID " + id, e);
        }
    }
}
