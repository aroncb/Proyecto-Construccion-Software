package com.example.demo.producto;

import java.util.List;

// Repository (interface): declara los métodos de acceso a la BD
public interface ProductoRepository {

    List<Producto> listar();

    List<Producto> listarDisponibles();

    Producto obtenerPorId(int id);

    void registrar(Producto producto);

    void actualizar(Producto producto);

    void cambiarDisponibilidad(int id, boolean disponible);
}
