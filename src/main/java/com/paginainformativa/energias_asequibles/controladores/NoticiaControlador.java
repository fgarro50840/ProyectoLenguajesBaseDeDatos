package com.paginainformativa.energias_asequibles.controladores;

import com.paginainformativa.energias_asequibles.modelos.Noticia;
import com.paginainformativa.energias_asequibles.services.interfaces.EnergiaService;
import com.paginainformativa.energias_asequibles.services.interfaces.NoticiaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.stereotype.Controller;

@Controller
@RequestMapping("/noticias")
public class NoticiaControlador {

    private final EnergiaService eService;
    private final NoticiaService nService;

    @Autowired
    public NoticiaControlador(EnergiaService eService, NoticiaService nService) {
        this.eService = eService;
        this.nService = nService;
    }

    @GetMapping
    public String listarNoticias(Model model) {
        List<Noticia> noticias = nService.listar();
        model.addAttribute("noticias", noticias);
        return "noticia/listaNoticias";
    }

    @GetMapping({"/formulario", "/formulario/{id}"})
    public String mostrarFormulario(@PathVariable(value = "id", required = false) Long id, Model model) {
        model.addAttribute("energias", eService.listar());
        if (id == null) {
            model.addAttribute("noticia", new Noticia());
        } else {
            Noticia noticia = nService.porId(id);
            model.addAttribute("noticia", noticia);
        }
        return "noticia/datosNoticias";
    }

    @PostMapping("/guardar")
    public String guardarNoticia(@ModelAttribute Noticia noticia) {
        System.out.println("Noticia = " + noticia);
        if (noticia.getId() == null) {
            noticia.setActivo(true);
            nService.save(noticia);
        } else {
            Noticia noticiaExistente = nService.porId(noticia.getId());
            if (noticia.getEnergia() == null) {
                noticia.setEnergia(noticiaExistente.getEnergia());
            }
            if (noticia.getTitulo() == null || noticia.getTitulo().isBlank()) {
                noticia.setTitulo(noticiaExistente.getTitulo());
            }
            if (noticia.getInformacion() == null || noticia.getInformacion().isBlank()) {
                noticia.setInformacion(noticiaExistente.getInformacion());
            }
            nService.save(noticia);
        }
        return "redirect:/noticias";
    }

    @GetMapping("/desactivar/{id}")
    public String desactivar(Noticia noticia) {
        nService.desactivar(noticia.getId()); // Cambia el estado a inactivo
        return "redirect:/noticias"; // Redirige a la lista de noticias
    }

    @GetMapping("/reactivar/{id}")
    public String reactivar(Noticia noticia) {
        nService.reactivar(noticia.getId()); // Asegúrate de que este método reactive al usuario
        return "redirect:/noticias"; // Redirige a la lista de noticias
    }

}
