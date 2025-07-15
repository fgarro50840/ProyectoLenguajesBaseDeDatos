package com.paginainformativa.energias_asequibles.services.interfaces;

import com.paginainformativa.energias_asequibles.modelos.Indicador;

import java.util.List;

public interface IndicadorService {

    List<Indicador> listar();

    Indicador save(Indicador indicador);

    Indicador porId(Long id);

    Indicador porProyecto(Long proyectoId);

    void desactivar(Long id);

    void reactivar(Long id);

}
