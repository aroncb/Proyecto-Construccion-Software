package com.example.demo.categoria;

import java.util.List;

// Service (interface): declara las operaciones del negocio
public interface CategoriaService {

    List<Categoria> listar();

    List<Categoria> listarActivas();

    Categoria obtenerPorId(int id);

    void registrar(Categoria categoria);

    void actualizar(Categoria categoria);

    void cambiarEstado(int id, boolean activo);
}
