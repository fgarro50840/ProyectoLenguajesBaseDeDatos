package com.paginainformativa.energias_asequibles.services.implementations;

import com.paginainformativa.energias_asequibles.modelos.Rol;
import com.paginainformativa.energias_asequibles.repositorios.RolRepositorio;
import com.paginainformativa.energias_asequibles.repositorios_pl.RolRepositorioPl;
import com.paginainformativa.energias_asequibles.services.excepciones.EntidadNoEncontradaExcepcion;
import com.paginainformativa.energias_asequibles.services.excepciones.EntidadNoGuardadaExcepcion;
import com.paginainformativa.energias_asequibles.services.interfaces.RolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RolServiceImpl implements RolService {

    private final RolRepositorioPl rRepo;

    @Autowired
    public RolServiceImpl(RolRepositorioPl rRepo) {
        this.rRepo = rRepo;
    }

    @Override
    @Transactional
    public List<Rol> listar() {
        try {
            return rRepo.listar();
        } catch (Exception e) {
            throw new EntidadNoEncontradaExcepcion("No se pudieron listar los roles", e);
        }
    }

    @Override
    @Transactional
    public Rol guardar(Rol rol) {
        if (rol == null) {
            throw new EntidadNoGuardadaExcepcion("El rol no puede ser nulo");
        }
        try {
            return rRepo.crearRole(rol.getTipo());
        } catch (Exception e) {
            throw new EntidadNoGuardadaExcepcion("No se pudo guardar el rol", e);
        }
    }

    @Override
    @Transactional
    public Rol porId(Long id) {

        Rol rolTemp =  rRepo.porId(id);
        if(rolTemp == null) {
            throw new EntidadNoEncontradaExcepcion("Rol con ID " + id + " no encontrado");
        }

        return rolTemp;
    }

}
