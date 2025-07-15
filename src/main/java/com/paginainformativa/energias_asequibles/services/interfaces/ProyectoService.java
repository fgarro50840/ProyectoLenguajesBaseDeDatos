package com.paginainformativa.energias_asequibles.services.interfaces;

import com.paginainformativa.energias_asequibles.modelos.Proyecto;
import java.util.List;

public interface ProyectoService {

    List<Proyecto> listar();

    Proyecto save(Proyecto proyecto);

    Proyecto porId(Long id);

    Proyecto porEnergia(Long energiaId);

    void desactivar(Long id);

    void reactivar(Long id);

}
