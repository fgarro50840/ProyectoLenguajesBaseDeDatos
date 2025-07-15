package com.paginainformativa.energias_asequibles.services.interfaces;

import com.paginainformativa.energias_asequibles.modelos.Rol;
import java.util.List;

public interface RolService {

    List<Rol> listar();

    Rol guardar(Rol rol);

    Rol porId(Long id);

}
