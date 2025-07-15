package com.paginainformativa.energias_asequibles.services.interfaces;

import com.paginainformativa.energias_asequibles.modelos.OpcionMenu;
import java.util.List;

public interface OpcionMenuService {

    List<OpcionMenu> listar();

    OpcionMenu save(OpcionMenu favorito);

    OpcionMenu porId(Long id);

    void desactivar(Long id);

    void reactivar(Long id);

    List<OpcionMenu> listarActivos();

}
