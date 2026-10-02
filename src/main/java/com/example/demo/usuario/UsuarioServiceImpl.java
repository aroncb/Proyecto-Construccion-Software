package com.example.demo.usuario;

import java.util.List;

import org.springframework.stereotype.Service;

// Service (clase Impl): reglas de negocio de usuarios
@Service
public class UsuarioServiceImpl implements UsuarioService {

    private static final String ROL_ADMINISTRADOR = "ADMINISTRADOR";
    private static final String ROL_MOTORIZADO = "MOTORIZADO";
    private static final int CLAVE_MINIMA = 6;

    private final UsuarioRepository usuarioRepository;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public List<Usuario> listar() {
        return usuarioRepository.listar();
    }

    @Override
    public Usuario obtenerPorId(int id) {
        return usuarioRepository.obtenerPorId(id);
    }

    // Regla: solo ingresan usuarios activos con la clave correcta
    @Override
    public Usuario autenticar(String username, String clave) {
        if (username == null || clave == null) {
            return null;
        }
        Usuario usuario = usuarioRepository.obtenerPorUsername(username.trim().toLowerCase());
        if (usuario == null || !usuario.getActivo() || !usuario.getClave().equals(clave)) {
            return null;
        }
        return usuario;
    }

    @Override
    public List<Usuario> listarMotorizadosActivos() {
        return usuarioRepository.listarMotorizadosActivos();
    }

    // Regla: desde el sistema solo se registran motorizados (el administrador ya existe)
    @Override
    public String registrarMotorizado(Usuario usuario) {
        limpiarDatos(usuario);

        if (usuarioRepository.existeUsername(usuario.getUsername(), 0)) {
            return "El usuario '" + usuario.getUsername() + "' ya existe. Elige otro.";
        }
        if (usuario.getClave() == null || usuario.getClave().length() < CLAVE_MINIMA) {
            return "La clave debe tener al menos " + CLAVE_MINIMA + " caracteres.";
        }

        usuario.setRol(ROL_MOTORIZADO);
        usuarioRepository.registrar(usuario);
        return null;
    }

    // Regla: si la clave viene vacía, se mantiene la clave actual
    @Override
    public String actualizar(Usuario usuario) {
        limpiarDatos(usuario);

        if (usuarioRepository.existeUsername(usuario.getUsername(), usuario.getId())) {
            return "El usuario '" + usuario.getUsername() + "' ya existe. Elige otro.";
        }

        boolean cambiaClave = usuario.getClave() != null && !usuario.getClave().isBlank();
        if (cambiaClave) {
            if (usuario.getClave().length() < CLAVE_MINIMA) {
                return "La clave debe tener al menos " + CLAVE_MINIMA + " caracteres.";
            }
            usuarioRepository.actualizar(usuario);
        } else {
            usuarioRepository.actualizarSinClave(usuario);
        }
        return null;
    }

    // Regla: el administrador no se puede deshabilitar
    @Override
    public String cambiarEstado(int id, boolean activo) {
        Usuario usuario = usuarioRepository.obtenerPorId(id);
        if (usuario == null) {
            return "No se encontró el usuario.";
        }
        if (ROL_ADMINISTRADOR.equals(usuario.getRol()) && !activo) {
            return "El administrador no se puede deshabilitar.";
        }
        usuarioRepository.cambiarEstado(id, activo);
        return null;
    }

    private void limpiarDatos(Usuario usuario) {
        usuario.setNombre(usuario.getNombre().trim());
        usuario.setApellido(usuario.getApellido().trim());
        usuario.setUsername(usuario.getUsername().trim().toLowerCase());
    }
}
