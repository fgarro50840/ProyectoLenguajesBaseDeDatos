package com.paginainformativa.energias_asequibles.services.interfaces;

import com.paginainformativa.energias_asequibles.modelos.Presupuesto;
import java.util.List;

public interface PresupuestoService {

    List<Presupuesto> listar();

    Presupuesto save(Presupuesto presupuesto);

    Presupuesto porId(Long id);

    Presupuesto porProyecto(Long proyectoId);

    void desactivar(Long id);

    void reactivar(Long id);

}
