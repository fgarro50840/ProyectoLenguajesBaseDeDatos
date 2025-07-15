package com.paginainformativa.energias_asequibles.controladores;

import com.paginainformativa.energias_asequibles.modelos.Proyecto;
import com.paginainformativa.energias_asequibles.services.interfaces.EnergiaService;
import com.paginainformativa.energias_asequibles.services.interfaces.ProyectoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.stereotype.Controller;

@Controller
@RequestMapping("/proyectos")
public class ProyectoControlador {

    private final EnergiaService eService;
    private final ProyectoService pService;

    @Autowired
    public ProyectoControlador(EnergiaService eService, ProyectoService pService) {
        this.eService = eService;
        this.pService = pService;
    }

    @GetMapping
    public String listarProyectos(Model model) {
        List<Proyecto> proyectos = pService.listar();
        model.addAttribute("proyectos", proyectos);
        return "proyecto/listaProyectos";
    }

    @GetMapping({"/formulario", "/formulario/{id}"})
    public String mostrarFormulario(@PathVariable(value = "id", required = false) Long id, Model model) {
        model.addAttribute("energias", eService.listar());
        if (id == null) {
            model.addAttribute("proyecto", new Proyecto());
        } else {
            Proyecto proyecto = pService.porId(id);
            model.addAttribute("proyecto", proyecto);
        }
        return "proyecto/datosProyectos";
    }

    @PostMapping("/guardar")
    public String guardarProyecto(@ModelAttribute Proyecto proyecto) {
        System.out.println("Proyecto = " + proyecto);
        if (proyecto.getId() == null) {
            proyecto.setActivo(true);
            pService.save(proyecto);
        } else {
            Proyecto proyectoExistente = pService.porId(proyecto.getId());
            if (proyecto.getEnergia() == null) {
                proyecto.setEnergia(proyectoExistente.getEnergia());
            }
            if (proyecto.getNombre() == null || proyecto.getNombre().isBlank()) {
                proyecto.setNombre(proyectoExistente.getNombre());
            }
            if (proyecto.getDescripcion() == null || proyecto.getDescripcion().isBlank()) {
                proyecto.setDescripcion(proyectoExistente.getDescripcion());
            }
            pService.save(proyecto);
        }
        return "redirect:/proyectos";
    }

    @GetMapping("/desactivar/{id}")
    public String desactivar(@PathVariable Long id) {
        Proyecto proyecto = pService.porId(id);
        proyecto.setActivo(false);
        pService.save(proyecto);
        return "redirect:/proyectos";
    }

    @GetMapping("/reactivar/{id}")
    public String reactivar(@PathVariable Long id) {
        Proyecto proyecto = pService.porId(id);
        proyecto.setActivo(true);
        pService.save(proyecto);
        return "redirect:/proyectos";
    }

}
