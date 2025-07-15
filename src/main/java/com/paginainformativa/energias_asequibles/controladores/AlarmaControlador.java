package com.paginainformativa.energias_asequibles.controladores;

import com.paginainformativa.energias_asequibles.modelos.Alarma;
import com.paginainformativa.energias_asequibles.services.interfaces.AlarmaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.stereotype.Controller;

@Controller
@RequestMapping("/alarmas")
public class AlarmaControlador {

    private final AlarmaService aService;

    @Autowired
    public AlarmaControlador(AlarmaService aService) {
        this.aService = aService;
    }

    @GetMapping
    public String listarAlarmas(Model model) {
        List<Alarma> alarmas = aService.listar();
        model.addAttribute("alarmas", alarmas);
        return "alarma/listaAlarmas";
    }

    @GetMapping({"/formulario", "/formulario/{id}"})
    public String mostrarFormulario(@PathVariable(required = false) Long id, Model model) {
        if (id == null) {
            model.addAttribute("alarma", new Alarma());
        } else {
            Alarma alarma = aService.porId(id);
            model.addAttribute("alarma", alarma);
        }
        return "alarma/datosAlarmas";
    }

    @PostMapping("/guardar")
    public String guardarAlarma(@ModelAttribute Alarma alarma) {
        System.out.println("Alarma = " + alarma);
        if (alarma.getId() == null) {
            aService.save(alarma);
            return "redirect:/alarmas";
        } else {
            Alarma alarmaExistente = aService.porId(alarma.getId());
            if (alarma.getNombre() == null || alarma.getNombre().isBlank()) {
                alarma.setNombre(alarmaExistente.getNombre());
            }
            if (alarma.getCriticidad() == null || alarma.getCriticidad().isBlank()) {
                alarma.setCriticidad(alarmaExistente.getCriticidad());
            }
            if (alarma.getDescripcion() == null || alarma.getDescripcion().isBlank()) {
                alarma.setDescripcion(alarmaExistente.getDescripcion());
            }
            aService.save(alarma);
            return "redirect:/alarmas";
        }
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarAlarma(@PathVariable Long id) {
        aService.eliminar(id);
        return "redirect:/alarmas"; // Redirige a la lista de alarmas
    }

}
