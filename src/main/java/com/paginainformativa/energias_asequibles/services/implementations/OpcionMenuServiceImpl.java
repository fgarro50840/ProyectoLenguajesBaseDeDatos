package com.paginainformativa.energias_asequibles.services.implementations;

import com.paginainformativa.energias_asequibles.modelos.OpcionMenu;
import com.paginainformativa.energias_asequibles.repositorios_pl.OpcionMenuRepositorioPl;
import com.paginainformativa.energias_asequibles.services.excepciones.EntidadNoDesactivadaExcepcion;
import com.paginainformativa.energias_asequibles.services.excepciones.EntidadNoEncontradaExcepcion;
import com.paginainformativa.energias_asequibles.services.excepciones.EntidadNoGuardadaExcepcion;
import com.paginainformativa.energias_asequibles.services.interfaces.OpcionMenuService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpcionMenuServiceImpl implements OpcionMenuService {

    private final OpcionMenuRepositorioPl oRepo;

    @Autowired
    public OpcionMenuServiceImpl(OpcionMenuRepositorioPl oRepo) {
        this.oRepo = oRepo;
    }

    @Override
    @Transactional
    public List<OpcionMenu> listar() {
        try {
            return oRepo.listar();
        } catch (Exception e) {
            throw new EntidadNoEncontradaExcepcion("No se pudieron listar las opciones de menú", e);
        }
    }

    @Override
    @Transactional
    public OpcionMenu save(OpcionMenu opcionMenu) {
        if (opcionMenu == null) {
            throw new EntidadNoGuardadaExcepcion("La opción de menú no puede ser nula");
        }
        try {
            Long id = opcionMenu.getId() != null ? opcionMenu.getId() : 0L;
            boolean activo = opcionMenu.getId() == null ? true : opcionMenu.isActivo();

            return oRepo.save(id, opcionMenu.getNombre(), activo);
        } catch (Exception e) {
            throw new EntidadNoGuardadaExcepcion("No se pudo guardar la opción de menú", e);
        }
    }

    @Override
    @Transactional
    public OpcionMenu porId(Long id) {
        OpcionMenu resultado = oRepo.porId(id);
        if (resultado == null) {
            throw new EntidadNoEncontradaExcepcion("Opción de menú con ID " + id + " no encontrada");
        }
        return resultado;
    }

    @Override
    @Transactional
    public void desactivar(Long id) {
        try {
            OpcionMenu opcionMenu = porId(id);
            opcionMenu.setActivo(false);
            save(opcionMenu);
        } catch (Exception e) {
            throw new EntidadNoDesactivadaExcepcion("No se pudo desactivar la opción de menú con ID " + id, e);
        }
    }

    @Override
    @Transactional
    public void reactivar(Long id) {
        try {
            OpcionMenu opcionMenu = porId(id);
            opcionMenu.setActivo(true);
            save(opcionMenu);
        } catch (Exception e) {
            throw new EntidadNoDesactivadaExcepcion("No se pudo reactivar la opción de menú con ID " + id, e);
        }
    }

    @Override
    @Transactional
    public List<OpcionMenu> listarActivos() {
        return oRepo.listarActivos();
    }
}
