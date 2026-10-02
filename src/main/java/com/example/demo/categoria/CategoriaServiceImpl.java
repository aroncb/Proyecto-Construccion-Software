package com.example.demo.categoria;

import java.util.List;

import org.springframework.stereotype.Service;

// Service (clase Impl): lógica de negocio, delega el acceso a datos al repository
@Service
public class CategoriaServiceImpl implements CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public CategoriaServiceImpl(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    @Override
    public List<Categoria> listar() {
        return categoriaRepository.listar();
    }

    @Override
    public List<Categoria> listarActivas() {
        return categoriaRepository.listarActivas();
    }

    @Override
    public Categoria obtenerPorId(int id) {
        return categoriaRepository.obtenerPorId(id);
    }

    @Override
    public void registrar(Categoria categoria) {
        categoria.setNombre(categoria.getNombre().trim());
        categoriaRepository.registrar(categoria);
    }

    @Override
    public void actualizar(Categoria categoria) {
        categoria.setNombre(categoria.getNombre().trim());
        categoriaRepository.actualizar(categoria);
    }

    @Override
    public void cambiarEstado(int id, boolean activo) {
        categoriaRepository.cambiarEstado(id, activo);
    }
}
