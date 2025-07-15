package com.paginainformativa.energias_asequibles.services.implementations;

import com.paginainformativa.energias_asequibles.modelos.Usuario;
import com.paginainformativa.energias_asequibles.repositorios.UsuarioRepositorio;
import com.paginainformativa.energias_asequibles.repositorios_pl.UsuarioRepositorioPl;
import com.paginainformativa.energias_asequibles.services.excepciones.*;
import com.paginainformativa.energias_asequibles.services.interfaces.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepositorioPl uRepo;

    @Autowired
    public UsuarioServiceImpl(UsuarioRepositorioPl uRepo) {
        this.uRepo = uRepo;
    }

    @Override
    @Transactional
    public List<Usuario> listar() {
        try {
            return uRepo.listar().stream()
                    .peek(usuario -> usuario.setContrasena(null))
                    .collect(Collectors.toList());


        } catch (Exception e) {
            throw new EntidadNoEncontradaExcepcion("No se pudieron listar los usuarios", e);
        }
    }

    @Override
    @Transactional
    public Usuario save(Usuario usuario) {
        if (usuario == null) {
            throw new EntidadNoGuardadaExcepcion("El usuario no puede ser nulo");
        }
        try {
            Long id = usuario.getId() != null ? usuario.getId() : 0L;
            boolean activo = usuario.getId() == null ? true : usuario.isActivo();

            return uRepo.save(
                    id,
                    usuario.getNombre(),
                    usuario.getContrasena(),
                    usuario.getRol().getId(),
                    activo
            );
        } catch (Exception e) {
            throw new EntidadNoGuardadaExcepcion("No se pudo guardar el usuario", e);
        }
    }

    @Override
    @Transactional
    public Usuario porId(Long id) {
        Usuario usuario = uRepo.porId(id);
        if (usuario == null) {
            throw new EntidadNoEncontradaExcepcion("Usuario con ID " + id + " no encontrado");
        }
        return usuario;
    }

    @Override
    @Transactional
    public Usuario porNombre(String nombre) {
        Usuario usuario = uRepo.porNombre(nombre);
        if (usuario == null) {
            throw new EntidadNoEncontradaExcepcion("Usuario con nombre " + nombre + " no encontrado");
        }
        return usuario;
    }

    @Transactional
    @Override
    public void desactivar(Long id) {
        try {
            Usuario usuario = porId(id);

            usuario.setActivo(false);

            save(usuario);
        } catch (Exception e) {
            throw new EntidadNoDesactivadaExcepcion("No se pudo desactivar el usuario con ID " + id, e);
        }
    }

    @Transactional
    @Override
    public void reactivar(Long id) {
        try {
            Usuario usuario = porId(id);

            usuario.setActivo(true);

            save(usuario);
        } catch (Exception e) {
            throw new EntidadNoDesactivadaExcepcion("No se pudo desactivar el usuario con ID " + id, e);
        }
    }

}
