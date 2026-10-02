package com.example.demo.usuario;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

// Repository (clase Impl): ejecuta las consultas SQL con JdbcTemplate
@Repository
public class UsuarioRepositoryImpl implements UsuarioRepository {

    private final JdbcTemplate jdbcTemplate;

    public UsuarioRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // RowMapper: convierte cada fila en un objeto Usuario
    private final RowMapper<Usuario> usuarioRowMapper = (rs, rowNum) -> new Usuario(
            rs.getInt("id"),
            rs.getString("nombre"),
            rs.getString("apellido"),
            rs.getString("username"),
            rs.getString("clave"),
            rs.getString("rol"),
            rs.getBoolean("activo")
    );

    @Override
    public List<Usuario> listar() {
        String sql = "SELECT id, nombre, apellido, username, clave, rol, activo FROM usuario ORDER BY id";
        return jdbcTemplate.query(sql, usuarioRowMapper);
    }

    @Override
    public Usuario obtenerPorId(int id) {
        String sql = "SELECT id, nombre, apellido, username, clave, rol, activo FROM usuario WHERE id = ?";
        List<Usuario> resultado = jdbcTemplate.query(sql, usuarioRowMapper, id);
        return resultado.isEmpty() ? null : resultado.get(0);
    }

    // Motorizados activos con la cantidad de pedidos que tienen en curso,
    // ordenados del menos ocupado al más ocupado (ayuda al admin a repartir)
    @Override
    public List<Usuario> listarMotorizadosActivos() {
        String sql = "SELECT u.id, u.nombre, u.apellido, u.username, u.clave, u.rol, u.activo, "
                + "(SELECT COUNT(*) FROM pedido p WHERE p.id_motorizado = u.id "
                + " AND p.estado IN ('ASIGNADO', 'ACEPTADO')) AS en_curso "
                + "FROM usuario u WHERE u.rol = 'MOTORIZADO' AND u.activo = TRUE "
                + "ORDER BY en_curso, u.nombre";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Usuario usuario = usuarioRowMapper.mapRow(rs, rowNum);
            usuario.setPedidosEnCurso(rs.getInt("en_curso"));
            return usuario;
        });
    }

    // Cuenta cuántos usuarios tienen ese username, sin contar al usuario que se está editando
    @Override
    public boolean existeUsername(String username, int excluirId) {
        String sql = "SELECT COUNT(*) FROM usuario WHERE username = ? AND id <> ?";
        Integer total = jdbcTemplate.queryForObject(sql, Integer.class, username, excluirId);
        return total != null && total > 0;
    }

    @Override
    public void registrar(Usuario usuario) {
        String sql = "INSERT INTO usuario (nombre, apellido, username, clave, rol, activo) "
                + "VALUES (?, ?, ?, ?, ?, TRUE)";
        jdbcTemplate.update(sql,
                usuario.getNombre(),
                usuario.getApellido(),
                usuario.getUsername(),
                usuario.getClave(),
                usuario.getRol());
    }

    @Override
    public void actualizar(Usuario usuario) {
        String sql = "UPDATE usuario SET nombre = ?, apellido = ?, username = ?, clave = ? WHERE id = ?";
        jdbcTemplate.update(sql,
                usuario.getNombre(),
                usuario.getApellido(),
                usuario.getUsername(),
                usuario.getClave(),
                usuario.getId());
    }

    @Override
    public void actualizarSinClave(Usuario usuario) {
        String sql = "UPDATE usuario SET nombre = ?, apellido = ?, username = ? WHERE id = ?";
        jdbcTemplate.update(sql,
                usuario.getNombre(),
                usuario.getApellido(),
                usuario.getUsername(),
                usuario.getId());
    }

    @Override
    public void cambiarEstado(int id, boolean activo) {
        String sql = "UPDATE usuario SET activo = ? WHERE id = ?";
        jdbcTemplate.update(sql, activo, id);
    }
}
