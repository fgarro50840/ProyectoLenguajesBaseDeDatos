package com.paginainformativa.energias_asequibles.services.interfaces;

import com.paginainformativa.energias_asequibles.modelos.Energia;
import java.util.List;

public interface EnergiaService {

    List<Energia> listar();

    Energia save(Energia energia);

    Energia porId(Long id);

    void desactivar(Long id);

    void reactivar(Long id);
}
