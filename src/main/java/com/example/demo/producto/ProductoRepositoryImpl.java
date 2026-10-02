package com.example.demo.producto;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

// Repository (clase Impl): ejecuta las consultas SQL con JdbcTemplate
@Repository
public class ProductoRepositoryImpl implements ProductoRepository {

    private final JdbcTemplate jdbcTemplate;

    public ProductoRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Consulta base: producto + nombre de su categoría
    private static final String SELECT_BASE =
            "SELECT p.id, p.nombre, p.descripcion, p.precio, p.disponible, p.id_categoria, "
            + "c.nombre AS categoria_nombre "
            + "FROM producto p INNER JOIN categoria c ON p.id_categoria = c.id ";

    // RowMapper: convierte cada fila en un objeto Producto
    private final RowMapper<Producto> productoRowMapper = (rs, rowNum) -> new Producto(
            rs.getInt("id"),
            rs.getString("nombre"),
            rs.getString("descripcion"),
            rs.getBigDecimal("precio"),
            rs.getBoolean("disponible"),
            rs.getInt("id_categoria"),
            rs.getString("categoria_nombre")
    );

    @Override
    public List<Producto> listar() {
        String sql = SELECT_BASE + "ORDER BY p.id";
        return jdbcTemplate.query(sql, productoRowMapper);
    }

    // Catálogo del cliente: solo productos disponibles de categorías activas
    @Override
    public List<Producto> listarDisponibles() {
        String sql = SELECT_BASE + "WHERE p.disponible = TRUE AND c.activo = TRUE ORDER BY c.nombre, p.nombre";
        return jdbcTemplate.query(sql, productoRowMapper);
    }

    @Override
    public Producto obtenerPorId(int id) {
        String sql = SELECT_BASE + "WHERE p.id = ?";
        List<Producto> resultado = jdbcTemplate.query(sql, productoRowMapper, id);
        return resultado.isEmpty() ? null : resultado.get(0);
    }

    @Override
    public void registrar(Producto producto) {
        String sql = "INSERT INTO producto (nombre, descripcion, precio, disponible, id_categoria) "
                + "VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                producto.getNombre(),
                producto.getDescripcion(),
                producto.getPrecio(),
                producto.getDisponible(),
                producto.getIdCategoria());
    }

    @Override
    public void actualizar(Producto producto) {
        String sql = "UPDATE producto SET nombre = ?, descripcion = ?, precio = ?, id_categoria = ? "
                + "WHERE id = ?";
        jdbcTemplate.update(sql,
                producto.getNombre(),
                producto.getDescripcion(),
                producto.getPrecio(),
                producto.getIdCategoria(),
                producto.getId());
    }

    @Override
    public void cambiarDisponibilidad(int id, boolean disponible) {
        String sql = "UPDATE producto SET disponible = ? WHERE id = ?";
        jdbcTemplate.update(sql, disponible, id);
    }
}
