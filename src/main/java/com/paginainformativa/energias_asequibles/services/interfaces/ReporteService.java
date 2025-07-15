package com.paginainformativa.energias_asequibles.services.interfaces;

import com.paginainformativa.energias_asequibles.modelos.Reporte;
import java.util.List;

public interface ReporteService {

    List<Reporte> listar();

    Reporte save(Reporte reporte);

    Reporte porId(Long id);

    Reporte porProyecto(Long proyectoId);

    void desactivar(Long id);

    void reactivar(Long id);

}
