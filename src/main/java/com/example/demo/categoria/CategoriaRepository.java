package com.example.demo.categoria;

import java.util.List;

// Repository (interface): declara los métodos de acceso a la BD
public interface CategoriaRepository {

    List<Categoria> listar();

    List<Categoria> listarActivas();

    Categoria obtenerPorId(int id);

    void registrar(Categoria categoria);

    void actualizar(Categoria categoria);

    void cambiarEstado(int id, boolean activo);
}
