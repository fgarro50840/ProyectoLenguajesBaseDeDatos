package com.paginainformativa.energias_asequibles.services.interfaces;

import com.paginainformativa.energias_asequibles.modelos.Noticia;
import java.util.List;

public interface NoticiaService {

    List<Noticia> listar();

    Noticia save(Noticia noticia);

    Noticia porId(Long id);

    Noticia porEnergia(Long energiaId);

    void desactivar(Long id);

    void reactivar(Long id);

}
