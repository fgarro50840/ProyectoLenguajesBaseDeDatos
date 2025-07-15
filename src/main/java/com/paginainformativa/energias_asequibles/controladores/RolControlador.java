package com.paginainformativa.energias_asequibles.controladores;

import com.paginainformativa.energias_asequibles.modelos.Rol;
import com.paginainformativa.energias_asequibles.services.interfaces.RolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/roles")
public class RolControlador {

    private final RolService rService;

    @Autowired
    public RolControlador(RolService rService) {
        this.rService = rService;
    }

    @RequestMapping
    public List<Rol> listarRoles() {
        return rService.listar();
    }

    @PostMapping("/guardar")
    public Rol guardarRol(@RequestBody Rol rol) {

        return rService.guardar(rol);
    }

}
