package com.paginainformativa.energias_asequibles.services.interfaces;

import com.paginainformativa.energias_asequibles.modelos.Usuario;
import java.util.List;


public interface UsuarioService {

    List<Usuario> listar();

    Usuario save(Usuario usuario);

    Usuario porId(Long id);

    Usuario porNombre(String nombre);

    void desactivar(Long id);

    void reactivar(Long id);
}
