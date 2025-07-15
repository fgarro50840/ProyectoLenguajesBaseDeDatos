package com.paginainformativa.energias_asequibles.controladores;

import com.paginainformativa.energias_asequibles.modelos.Indicador;
import com.paginainformativa.energias_asequibles.services.interfaces.IndicadorService;
import com.paginainformativa.energias_asequibles.services.interfaces.ProyectoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.stereotype.Controller;

@Controller
@RequestMapping("/indicadores")
public class IndicadorControlador {

    private final IndicadorService iService;
    private final ProyectoService pService;

    @Autowired
    public IndicadorControlador(IndicadorService iService, ProyectoService pService) {
        this.iService = iService;
        this.pService = pService;
    }

    @GetMapping
    public String listarEnergias(Model model) {
        List<Indicador> indicadores = iService.listar();
        model.addAttribute("indicadores", indicadores);
        return "indicador/listaIndicadores";
    }

    @GetMapping({"/formulario", "/formulario/{id}"})
    public String mostrarFormulario(@PathVariable(required = false) Long id, Model model) {
        model.addAttribute("proyectos", pService.listar());
        if (id == null) {
            model.addAttribute("indicador", new Indicador());
        } else {
            Indicador indicador = iService.porId(id);
            model.addAttribute("indicador", indicador);
        }
        return "indicador/datosIndicadores";
    }

    @PostMapping("/guardar")
    public String guardarEnergia(@ModelAttribute Indicador indicador) {
        System.out.println("Indicador = " + indicador);
        if (indicador.getId() == null) {
            indicador.setActivo(true);
            iService.save(indicador);
        } else {
            Indicador indicadorExistente = iService.porId(indicador.getId());
            indicador.setActivo(indicadorExistente.isActivo());
            if (indicador.getProyecto() == null) {
                indicador.setProyecto(indicadorExistente.getProyecto());
            }
            if (indicador.getRendimiento() == 0) {
                indicador.setRendimiento(indicadorExistente.getRendimiento());
            }
        }
        return "redirect:/indicadores";
    }

    @GetMapping("/desactivar/{id}")
    public String desactivarEnergia(@PathVariable Long id) {
        iService.desactivar(id);
        return "redirect:/indicadores"; // Redirige a la lista de indicadores
    }

    @GetMapping("/reactivar/{id}")
    public String reactivarEnergia(@PathVariable Long id) {
        iService.reactivar(id);
        return "redirect:/indicadores"; // Redirige a la lista de indicadores
    }

}
