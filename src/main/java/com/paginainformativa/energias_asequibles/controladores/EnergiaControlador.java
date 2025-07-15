package com.paginainformativa.energias_asequibles.controladores;

import com.paginainformativa.energias_asequibles.modelos.Energia;
import com.paginainformativa.energias_asequibles.services.interfaces.EnergiaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.stereotype.Controller;

@Controller
@RequestMapping("/energias")
public class EnergiaControlador {

    private final EnergiaService eService;

    @Autowired
    public EnergiaControlador(EnergiaService eService) {
        this.eService = eService;
    }

    @GetMapping
    public String listarEnergias(Model model) {
        List<Energia> energias = eService.listar();
        model.addAttribute("energias", energias);
        return "energia/listaEnergias";
    }

    @GetMapping({"/formulario", "/formulario/{id}"})
    public String mostrarFormulario(@PathVariable(required = false) Long id, Model model) {
        if (id == null) {
            model.addAttribute("energia", new Energia());
        } else {
            Energia energia = eService.porId(id);
            model.addAttribute("energia", energia);
        }
        return "energia/datosEnergias";
    }

    @PostMapping("/guardar")
    public String guardarEnergia(@ModelAttribute Energia energia) {
        System.out.println("Energia = " + energia);
        if (energia.getId() == null) {
            energia.setActivo(true);
            eService.save(energia);
            return "redirect:/energias";
        } else {
            Energia energiaExistente = eService.porId(energia.getId());
            energia.setActivo(energiaExistente.isActivo());
            if (energia.getNombre() == null || energia.getNombre().isBlank()) {
                energia.setNombre(energiaExistente.getNombre());
            }
            if (energia.getDescripcion() == null || energia.getDescripcion().isBlank()) {
                energia.setDescripcion(energiaExistente.getDescripcion());
            }
            eService.save(energia);
            return "redirect:/energias";
        }
    }

    @GetMapping("/desactivar/{id}")
    public String desactivarEnergia(@PathVariable Long id) {
        eService.desactivar(id);
        return "redirect:/energias"; // Redirige a la lista de energías
    }

    @GetMapping("/reactivar/{id}")
    public String reactivarEnergia(@PathVariable Long id) {
        eService.reactivar(id);
        return "redirect:/energias"; // Redirige a la lista de energías
    }

}
