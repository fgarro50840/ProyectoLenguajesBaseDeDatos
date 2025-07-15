package com.paginainformativa.energias_asequibles.controladores;

import com.paginainformativa.energias_asequibles.modelos.Energia;
import com.paginainformativa.energias_asequibles.modelos.Informacion;
import com.paginainformativa.energias_asequibles.services.interfaces.EnergiaService;
import com.paginainformativa.energias_asequibles.services.interfaces.InformacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.stereotype.Controller;

@Controller
@RequestMapping("/informaciones")
public class InformacionControlador {

    private final EnergiaService eService;
    private final InformacionService iService;

    @Autowired
    public InformacionControlador(EnergiaService eService, InformacionService iService) {
        this.eService = eService;
        this.iService = iService;
    }

    @GetMapping
    public String listarInformacions(Model model) {
        List<Informacion> informacions = iService.listar();
        model.addAttribute("informaciones", informacions);
        return "informacion/listaInformaciones";
    }

    @GetMapping({"/formulario", "/formulario/{id}"})
    public String mostrarFormulario(@PathVariable(value = "id", required = false) Long id, Model model) {
        model.addAttribute("energias", eService.listar());
        if (id == null) {
            model.addAttribute("informacion", new Informacion());
        } else {
            Informacion informacion = iService.porId(id);
            model.addAttribute("informacion", informacion);
        }
        return "informacion/datosInformaciones";
    }

    @PostMapping("/guardar")
    public String guardarInformacion(@ModelAttribute Informacion informacion) {
        System.out.println("Informacion recibida = " + informacion);
        if (informacion.getEnergia() != null && informacion.getEnergia().getId() != null) {
            Energia energiaCompleta = eService.porId(informacion.getEnergia().getId());
            informacion.setEnergia(energiaCompleta);
        }
        if (informacion.getId() == null) {
            informacion.setActivo(true);
            iService.save(informacion);
        } else {
            Informacion informacionExistente = iService.porId(informacion.getId());
            if (informacion.getInfoTexto() == null || informacion.getInfoTexto().isBlank()) {
                informacion.setInfoTexto(informacionExistente.getInfoTexto());
            }
            iService.save(informacion);
        }
        return "redirect:/informaciones";
    }

    @GetMapping("/desactivar/{id}")
    public String desactivar(@PathVariable Long id) {
        iService.desactivar(id);
        return "redirect:/informaciones";
    }

    @GetMapping("/reactivar/{id}")
    public String reactivar(@PathVariable Long id) {
        iService.reactivar(id);
        return "redirect:/informaciones";
    }

}
