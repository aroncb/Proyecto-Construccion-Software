package com.example.demo.producto;

import java.util.List;

// Service (interface): declara las operaciones del negocio
public interface ProductoService {

    List<Producto> listar();

    List<Producto> listarDisponibles();

    Producto obtenerPorId(int id);

    void registrar(Producto producto);

    void actualizar(Producto producto);

    void cambiarDisponibilidad(int id, boolean disponible);
}
