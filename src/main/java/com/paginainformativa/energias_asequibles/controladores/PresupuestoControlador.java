package com.paginainformativa.energias_asequibles.controladores;

import com.paginainformativa.energias_asequibles.modelos.Presupuesto;
import com.paginainformativa.energias_asequibles.services.interfaces.ProyectoService;
import com.paginainformativa.energias_asequibles.services.interfaces.PresupuestoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.stereotype.Controller;

@Controller
@RequestMapping("/presupuestos")
public class PresupuestoControlador {

    private final PresupuestoService pService;
    private final ProyectoService prService;

    @Autowired
    public PresupuestoControlador(PresupuestoService pService, ProyectoService prService) {
        this.pService = pService;
        this.prService = prService;
    }

    @GetMapping
    public String listarEnergias(Model model) {
        List<Presupuesto> presupuestos = pService.listar();
        model.addAttribute("presupuestos", presupuestos);
        return "presupuesto/listaPresupuestos";
    }

    @GetMapping({"/formulario", "/formulario/{id}"})
    public String mostrarFormulario(@PathVariable(required = false) Long id, Model model) {
        model.addAttribute("proyectos", prService.listar());
        if (id == null) {
            model.addAttribute("presupuesto", new Presupuesto());
        } else {
            Presupuesto presupuesto = pService.porId(id);
            model.addAttribute("presupuesto", presupuesto);
        }
        return "presupuesto/datosPresupuestos";
    }

    @PostMapping("/guardar")
    public String guardarEnergia(@ModelAttribute Presupuesto presupuesto) {
        System.out.println("Presupuesto = " + presupuesto);
        if (presupuesto.getId() == null) {
            presupuesto.setActivo(true);
            pService.save(presupuesto);
        } else {
            Presupuesto presupuestoExistente = pService.porId(presupuesto.getId());
            presupuesto.setActivo(presupuestoExistente.isActivo());
            if (presupuesto.getProyecto() == null) {
                presupuesto.setProyecto(presupuestoExistente.getProyecto());
            }
            if (presupuesto.getValor() == null) {
                presupuesto.setValor(presupuestoExistente.getValor());
            }
            pService.save(presupuesto);
        }
        return "redirect:/presupuestos";
    }

    @GetMapping("/desactivar/{id}")
    public String desactivarEnergia(@PathVariable Long id) {
        pService.desactivar(id);
        return "redirect:/presupuestos"; // Redirige a la lista de presupuestos
    }

    @GetMapping("/reactivar/{id}")
    public String reactivarEnergia(@PathVariable Long id) {
        pService.reactivar(id);
        return "redirect:/presupuestos"; // Redirige a la lista de presupuestos
    }

}
