package com.example.demo.producto;

import java.util.List;

import org.springframework.stereotype.Service;

// Service (clase Impl): lógica de negocio, delega el acceso a datos al repository
@Service
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository productoRepository;

    public ProductoServiceImpl(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    @Override
    public List<Producto> listar() {
        return productoRepository.listar();
    }

    @Override
    public List<Producto> listarDisponibles() {
        return productoRepository.listarDisponibles();
    }

    @Override
    public Producto obtenerPorId(int id) {
        return productoRepository.obtenerPorId(id);
    }

    @Override
    public void registrar(Producto producto) {
        producto.setNombre(producto.getNombre().trim());
        // Regla de negocio: si no se indica disponibilidad, el producto nace disponible
        if (producto.getDisponible() == null) {
            producto.setDisponible(true);
        }
        productoRepository.registrar(producto);
    }

    @Override
    public void actualizar(Producto producto) {
        producto.setNombre(producto.getNombre().trim());
        productoRepository.actualizar(producto);
    }

    @Override
    public void cambiarDisponibilidad(int id, boolean disponible) {
        productoRepository.cambiarDisponibilidad(id, disponible);
    }
}
