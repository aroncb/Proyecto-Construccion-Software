package com.example.demo.usuario;

import java.util.List;

// Repository (interface): declara los métodos de acceso a la BD
public interface UsuarioRepository {

    List<Usuario> listar();

    Usuario obtenerPorId(int id);

    boolean existeUsername(String username, int excluirId);

    void registrar(Usuario usuario);

    void actualizar(Usuario usuario);

    void actualizarSinClave(Usuario usuario);

    void cambiarEstado(int id, boolean activo);
}
