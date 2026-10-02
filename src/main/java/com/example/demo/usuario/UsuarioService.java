package com.example.demo.usuario;

import java.util.List;

// Service (interface): declara las operaciones del negocio
// Los métodos que devuelven String retornan null si todo salió bien,
// o el mensaje de error que se mostrará en la vista.
public interface UsuarioService {

    List<Usuario> listar();

    Usuario obtenerPorId(int id);

    String registrarMotorizado(Usuario usuario);

    String actualizar(Usuario usuario);

    String cambiarEstado(int id, boolean activo);
}
