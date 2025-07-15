package com.paginainformativa.energias_asequibles.services.interfaces;

import com.paginainformativa.energias_asequibles.modelos.Alarma;
import java.util.List;

public interface AlarmaService {

    List<Alarma> listar();

    Alarma save(Alarma energia);

    Alarma porId(Long id);

    void eliminar(Long id);
}
