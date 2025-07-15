package com.paginainformativa.energias_asequibles.controladores;

import com.paginainformativa.energias_asequibles.modelos.OpcionMenu;
import com.paginainformativa.energias_asequibles.services.interfaces.OpcionMenuService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class MenuControlador {

    private final OpcionMenuService oService;

    @Autowired
    public MenuControlador(OpcionMenuService oService) {
        this.oService = oService;
    }

    @GetMapping("/menu")
    public String menuPrincipal(Model model) {
        model.addAttribute("opcionesMenu", oService.listarActivos());
        return "menu/menuPrincipal";
    }

    @GetMapping("/opcionesMenu")
    public String listarOpcionesMenu(Model model) {
        List<OpcionMenu> opcionesMenu = oService.listar();
        model.addAttribute("opcionesMenu", opcionesMenu);
        return "menu/listaMenu";
    }

    @GetMapping("/menu/desactivar/{id}")
    public String desactivarEnergia(@PathVariable Long id) {
        oService.desactivar(id);
        return "redirect:/opcionesMenu"; // Redirige a la lista de opciones
    }

    @GetMapping("/menu/reactivar/{id}")
    public String reactivarEnergia(@PathVariable Long id) {
        oService.reactivar(id);
        return "redirect:/opcionesMenu"; // Redirige a la lista de opciones
    }

}
