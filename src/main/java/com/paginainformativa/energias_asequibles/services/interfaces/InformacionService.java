package com.paginainformativa.energias_asequibles.services.interfaces;

import com.paginainformativa.energias_asequibles.modelos.Informacion;
import java.util.List;

public interface InformacionService {

    List<Informacion> listar();

    Informacion save(Informacion informacion);

    Informacion porId(Long id);

    Informacion porEnergia(Long informacionId);

    void desactivar(Long id);

    void reactivar(Long id);

}
