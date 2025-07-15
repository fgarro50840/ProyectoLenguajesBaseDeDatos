package com.paginainformativa.energias_asequibles.services.implementations;

import com.paginainformativa.energias_asequibles.modelos.Indicador;
import com.paginainformativa.energias_asequibles.repositorios.IndicadorRepositorio;
import com.paginainformativa.energias_asequibles.repositorios_pl.IndicadorRepositorioPl;
import com.paginainformativa.energias_asequibles.services.excepciones.EntidadNoDesactivadaExcepcion;
import com.paginainformativa.energias_asequibles.services.excepciones.EntidadNoEncontradaExcepcion;
import com.paginainformativa.energias_asequibles.services.excepciones.EntidadNoGuardadaExcepcion;
import com.paginainformativa.energias_asequibles.services.interfaces.IndicadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

@Service
public class IndicadorServiceImpl implements IndicadorService {

    private final IndicadorRepositorioPl iRepo;

    @Autowired
    public IndicadorServiceImpl(IndicadorRepositorioPl iRepo) {
        this.iRepo = iRepo;
    }

    @Override
    @Transactional
    public List<Indicador> listar() {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            boolean esAdmin = auth != null && auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"));
            List<Indicador> todas = iRepo.listar();
            return esAdmin ? todas : todas.stream().filter(Indicador::isActivo).toList();
        } catch (Exception e) {
            throw new EntidadNoEncontradaExcepcion("No se pudieron listar los indicadores", e);
        }
    }

    @Override
    @Transactional
    public Indicador save(Indicador indicador) {
        if (indicador == null) {
            throw new EntidadNoGuardadaExcepcion("El indicador no puede ser nulo");
        }
        try {
            Long id = indicador.getId() != null ? indicador.getId() : 0L;
            boolean activo = indicador.getId() == null ? true : indicador.isActivo();
            return iRepo.save(
                    id,
                    indicador.getProyecto().getId(),
                    indicador.getRendimiento(),
                    activo
            );
        } catch (Exception e) {
            throw new EntidadNoGuardadaExcepcion("No se pudo guardar el indicador", e);
        }
    }

    @Override
    @Transactional
    public Indicador porId(Long id) {
        Indicador resultado = iRepo.porId(id);
        if (resultado == null) {
            throw new EntidadNoEncontradaExcepcion("Indicador con ID " + id + " no encontrado");
        }
        return resultado;
    }

    @Override
    public Indicador porProyecto(Long proyectoId) {
        Indicador resultado = iRepo.porProyecto(proyectoId);
        if (resultado == null) {
            throw new EntidadNoEncontradaExcepcion("Indicador para proyecto ID " + proyectoId + " no encontrado");
        }
        return resultado;
    }

    @Transactional
    @Override
    public void desactivar(Long id) {
        try {
            Indicador indicador = porId(id);
            indicador.setActivo(false);
            save(indicador);
        } catch (Exception e) {
            throw new EntidadNoDesactivadaExcepcion("No se pudo desactivar el indicador con ID " + id, e);
        }
    }

    @Transactional
    @Override
    public void reactivar(Long id) {
        try {
            Indicador indicador = porId(id);
            indicador.setActivo(true);
            save(indicador);
        } catch (Exception e) {
            throw new EntidadNoDesactivadaExcepcion("No se pudo reactivar el indicador con ID " + id, e);
        }
    }
}
