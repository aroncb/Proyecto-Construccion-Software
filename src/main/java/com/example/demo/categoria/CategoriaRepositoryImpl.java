package com.example.demo.categoria;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

// Repository (clase Impl): ejecuta las consultas SQL con JdbcTemplate
@Repository
public class CategoriaRepositoryImpl implements CategoriaRepository {

    private final JdbcTemplate jdbcTemplate;

    public CategoriaRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // RowMapper: convierte cada fila de la tabla en un objeto Categoria
    private final RowMapper<Categoria> categoriaRowMapper = (rs, rowNum) -> new Categoria(
            rs.getInt("id"),
            rs.getString("nombre"),
            rs.getString("descripcion"),
            rs.getBoolean("activo")
    );

    @Override
    public List<Categoria> listar() {
        String sql = "SELECT id, nombre, descripcion, activo FROM categoria ORDER BY id";
        return jdbcTemplate.query(sql, categoriaRowMapper);
    }

    @Override
    public List<Categoria> listarActivas() {
        String sql = "SELECT id, nombre, descripcion, activo FROM categoria WHERE activo = TRUE ORDER BY nombre";
        return jdbcTemplate.query(sql, categoriaRowMapper);
    }

    @Override
    public Categoria obtenerPorId(int id) {
        String sql = "SELECT id, nombre, descripcion, activo FROM categoria WHERE id = ?";
        List<Categoria> resultado = jdbcTemplate.query(sql, categoriaRowMapper, id);
        return resultado.isEmpty() ? null : resultado.get(0);
    }

    @Override
    public void registrar(Categoria categoria) {
        String sql = "INSERT INTO categoria (nombre, descripcion, activo) VALUES (?, ?, TRUE)";
        jdbcTemplate.update(sql, categoria.getNombre(), categoria.getDescripcion());
    }

    @Override
    public void actualizar(Categoria categoria) {
        String sql = "UPDATE categoria SET nombre = ?, descripcion = ? WHERE id = ?";
        jdbcTemplate.update(sql, categoria.getNombre(), categoria.getDescripcion(), categoria.getId());
    }

    @Override
    public void cambiarEstado(int id, boolean activo) {
        String sql = "UPDATE categoria SET activo = ? WHERE id = ?";
        jdbcTemplate.update(sql, activo, id);
    }
}
