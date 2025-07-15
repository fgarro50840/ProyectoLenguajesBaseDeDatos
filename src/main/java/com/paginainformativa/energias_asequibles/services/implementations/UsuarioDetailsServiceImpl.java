package com.paginainformativa.energias_asequibles.services.implementations;

import com.paginainformativa.energias_asequibles.modelos.Usuario;
import com.paginainformativa.energias_asequibles.modelos.UsuarioDetails;
import com.paginainformativa.energias_asequibles.services.excepciones.EntidadNoEncontradaExcepcion;
import com.paginainformativa.energias_asequibles.services.interfaces.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UsuarioDetailsServiceImpl implements UserDetailsService {

    private final UsuarioService uService;

    @Autowired
    public UsuarioDetailsServiceImpl(UsuarioService uService) {
        this.uService = uService;
    }

    @Override
    public UserDetails loadUserByUsername(String usuarioNombre) throws UsernameNotFoundException {

        try {
            Usuario usuario = uService.porNombre(usuarioNombre);
            return new UsuarioDetails(usuario);
        } catch (EntidadNoEncontradaExcepcion e) {
            throw new UsernameNotFoundException("Usuario no encontrado: " + usuarioNombre);
        }
    }
}
