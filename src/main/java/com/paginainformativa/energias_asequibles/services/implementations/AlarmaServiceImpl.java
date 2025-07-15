package com.paginainformativa.energias_asequibles.services.implementations;

import com.paginainformativa.energias_asequibles.modelos.Alarma;
import com.paginainformativa.energias_asequibles.repositorios.AlarmaRepositorio;
import com.paginainformativa.energias_asequibles.repositorios_pl.AlarmaRepositorioPl;
import com.paginainformativa.energias_asequibles.services.excepciones.EntidadNoDesactivadaExcepcion;
import com.paginainformativa.energias_asequibles.services.excepciones.EntidadNoEncontradaExcepcion;
import com.paginainformativa.energias_asequibles.services.excepciones.EntidadNoGuardadaExcepcion;
import com.paginainformativa.energias_asequibles.services.interfaces.AlarmaService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
public class AlarmaServiceImpl implements AlarmaService {

    private final AlarmaRepositorioPl aRepo;

    @Autowired
    public AlarmaServiceImpl(AlarmaRepositorioPl aRepo) {
        this.aRepo = aRepo;
    }

    @Override
    @Transactional
    public List<Alarma> listar() {
        try {
            return aRepo.listar();
        } catch (Exception e) {
            throw new EntidadNoEncontradaExcepcion("No se pudieron listar las alarmas", e);
        }
    }


    @Override
    @Transactional
    public Alarma save(Alarma alarma) {
        if (alarma == null) {
            throw new EntidadNoGuardadaExcepcion("La alarma no puede ser nula");
        }

        try {
            Long id = alarma.getId() != null ? alarma.getId() : 0L;

            return aRepo.save(
                    id,
                    alarma.getNombre(),
                    alarma.getCriticidad(),
                    alarma.getDescripcion()
            );
        } catch (Exception e) {
            throw new EntidadNoGuardadaExcepcion("No se pudo guardar la alarma", e);
        }
    }

    @Override
    @Transactional
    public Alarma porId(Long id) {
        Alarma alarma = aRepo.porId(id);
        if (alarma == null) {
            throw new EntidadNoEncontradaExcepcion("Alarma con ID " + id + " no encontrada");
        }
        return alarma;
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        try {
            aRepo.eliminar(id);
        } catch (Exception e) {
            throw new EntidadNoDesactivadaExcepcion("No se pudo eliminar la alarma con ID " + id, e);
        }
    }
}
