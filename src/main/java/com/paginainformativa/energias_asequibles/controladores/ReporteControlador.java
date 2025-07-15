package com.paginainformativa.energias_asequibles.controladores;

import com.paginainformativa.energias_asequibles.modelos.Reporte;
import com.paginainformativa.energias_asequibles.services.interfaces.ProyectoService;
import com.paginainformativa.energias_asequibles.services.interfaces.ReporteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.stereotype.Controller;

@Controller
@RequestMapping("/reportes")
public class ReporteControlador {

    private final ReporteService rService;
    private final ProyectoService pService;

    @Autowired
    public ReporteControlador(ReporteService rService, ProyectoService pService) {
        this.rService = rService;
        this.pService = pService;
    }

    @GetMapping
    public String listarEnergias(Model model) {
        List<Reporte> reportes = rService.listar();
        model.addAttribute("reportes", reportes);
        return "reporte/listaReportes";
    }

    @GetMapping({"/formulario", "/formulario/{id}"})
    public String mostrarFormulario(@PathVariable(required = false) Long id, Model model) {
        model.addAttribute("proyectos", pService.listar());
        if (id == null) {
            model.addAttribute("reporte", new Reporte());
        } else {
            Reporte reporte = rService.porId(id);
            model.addAttribute("reporte", reporte);
        }
        return "reporte/datosReportes";
    }

    @PostMapping("/guardar")
    public String guardarEnergia(@ModelAttribute Reporte reporte) {
        System.out.println("Reporte = " + reporte);
        if (reporte.getId() == null) {
            reporte.setActivo(true);
            rService.save(reporte);
        } else {
            Reporte reporteExistente = rService.porId(reporte.getId());
            reporte.setActivo(reporteExistente.isActivo());
            if (reporte.getProyecto() == null) {
                reporte.setProyecto(reporteExistente.getProyecto());
            }
            if (reporte.getInformacion() == null || reporte.getInformacion().isBlank()) {
                reporte.setInformacion(reporteExistente.getInformacion());
            }
            rService.save(reporte);
        }
        return "redirect:/reportes";
    }

    @GetMapping("/desactivar/{id}")
    public String desactivarEnergia(@PathVariable Long id) {
        rService.desactivar(id);
        return "redirect:/reportes"; // Redirige a la lista de reportes
    }

    @GetMapping("/reactivar/{id}")
    public String reactivarEnergia(@PathVariable Long id) {
        rService.reactivar(id);
        return "redirect:/reportes"; // Redirige a la lista de reportes
    }

}
