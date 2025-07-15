package com.paginainformativa.energias_asequibles.controladores;

import com.paginainformativa.energias_asequibles.modelos.Usuario;
import com.paginainformativa.energias_asequibles.modelos.UsuarioDetails;
import com.paginainformativa.energias_asequibles.services.interfaces.RolService;
import com.paginainformativa.energias_asequibles.services.interfaces.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.stereotype.Controller;

@Controller
@RequestMapping("/usuarios")
public class UsuarioControlador {

    private final UsuarioService uService;
    private final RolService rService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UsuarioControlador(UsuarioService uService, RolService rService, PasswordEncoder passwordEncoder) {
        this.uService = uService;
        this.rService = rService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public String listarUsuarios(Model model) {
        UsuarioDetails usuarioDetails = (UsuarioDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Usuario usuario = uService.porId(usuarioDetails.getId());
        List<Usuario> usuarios = uService.listar();
        usuarios.remove(usuario);
        model.addAttribute("usuarios", usuarios);
        return "usuario/listaUsuarios";
    }

    @GetMapping({"/formulario", "/formulario/{id}"})
    public String mostrarFormulario(@PathVariable(required = false) Long id, Model model) {
        model.addAttribute("roles", rService.listar());
        if (id == null) {
            model.addAttribute("usuario", new Usuario());
        } else {
            Usuario usuario = uService.porId(id);
            usuario.setContrasena(null);
            model.addAttribute("usuario", usuario);
        }
        return "usuario/datosUsuario";
    }

    @PostMapping("/guardar")
    public String guardarUsuario(@ModelAttribute Usuario usuario) {
        System.out.println("Usuario = " + usuario);
        if (usuario.getId() == null) {
            usuario.setActivo(true);
            usuario.setContrasena(passwordEncoder.encode(usuario.getContrasena()));
            uService.save(usuario);
            return "redirect:/index";
        } else {
            Usuario usuarioExistente = uService.porId(usuario.getId());
            usuario.setActivo(usuarioExistente.isActivo());
            if (usuario.getNombre().isBlank()) {
                usuario.setNombre(usuarioExistente.getNombre());
            }
            if (usuario.getContrasena().isBlank()) {
                usuario.setContrasena(usuarioExistente.getContrasena());
            } else {
                usuario.setContrasena(passwordEncoder.encode(usuario.getContrasena()));
            }
            if (usuario.getRol() == null) {
                usuario.setRol(usuarioExistente.getRol());
            }
            uService.save(usuario);
            return "redirect:/menu";
        }
    }

    @GetMapping("/desactivar/{id}")
    public String desactivarUsuario(Usuario usuario) {
        uService.desactivar(usuario.getId()); // Cambia el estado a inactivo
        return "redirect:/usuarios"; // Redirige a la lista de usuarios
    }

    @GetMapping("/reactivar/{id}")
    public String reactivarUsuario(Usuario usuario) {
        uService.reactivar(usuario.getId()); // Asegúrate de que este método reactive al usuario
        return "redirect:/usuarios"; // Redirige a la lista de usuarios
    }

}
