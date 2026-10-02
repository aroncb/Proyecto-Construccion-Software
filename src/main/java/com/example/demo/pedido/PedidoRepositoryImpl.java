package com.example.demo.pedido;

import java.sql.PreparedStatement;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

// Repository (clase Impl): ejecuta las consultas SQL con JdbcTemplate
@Repository
public class PedidoRepositoryImpl implements PedidoRepository {

    private final JdbcTemplate jdbcTemplate;

    public PedidoRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Consulta base: pedido + nombre del motorizado (si tiene uno asignado)
    private static final String SELECT_BASE =
            "SELECT p.id, p.cliente_nombre, p.cliente_telefono, p.cliente_direccion, p.fecha, "
            + "p.total, p.comprobante, p.estado, p.id_motorizado, "
            + "u.nombre AS motorizado_nombre, u.apellido AS motorizado_apellido "
            + "FROM pedido p LEFT JOIN usuario u ON p.id_motorizado = u.id ";

    // RowMapper: convierte cada fila en un objeto Pedido
    private final RowMapper<Pedido> pedidoRowMapper = (rs, rowNum) -> {
        Pedido pedido = new Pedido();
        pedido.setId(rs.getInt("id"));
        pedido.setClienteNombre(rs.getString("cliente_nombre"));
        pedido.setClienteTelefono(rs.getString("cliente_telefono"));
        pedido.setClienteDireccion(rs.getString("cliente_direccion"));
        pedido.setFecha(rs.getTimestamp("fecha").toLocalDateTime());
        pedido.setTotal(rs.getBigDecimal("total"));
        pedido.setComprobante(rs.getString("comprobante"));
        pedido.setEstado(rs.getString("estado"));

        // getObject devuelve null si el pedido aún no tiene motorizado
        pedido.setIdMotorizado((Integer) rs.getObject("id_motorizado"));
        if (rs.getString("motorizado_nombre") != null) {
            pedido.setMotorizadoNombre(rs.getString("motorizado_nombre") + " " + rs.getString("motorizado_apellido"));
        }
        return pedido;
    };

    // RowMapper: convierte cada fila en un objeto DetallePedido
    private final RowMapper<DetallePedido> detalleRowMapper = (rs, rowNum) -> {
        DetallePedido detalle = new DetallePedido();
        detalle.setId(rs.getInt("id"));
        detalle.setIdPedido(rs.getInt("id_pedido"));
        detalle.setIdProducto(rs.getInt("id_producto"));
        detalle.setProductoNombre(rs.getString("producto_nombre"));
        detalle.setCantidad(rs.getInt("cantidad"));
        detalle.setPrecioUnitario(rs.getBigDecimal("precio_unitario"));
        detalle.setSubtotal(rs.getBigDecimal("subtotal"));
        return detalle;
    };

    // Usa KeyHolder para recuperar el id que la BD genera al insertar,
    // porque ese id se necesita para guardar el detalle del pedido.
    @Override
    public int registrar(Pedido pedido) {
        String sql = "INSERT INTO pedido (cliente_nombre, cliente_telefono, cliente_direccion, total, comprobante, estado) "
                + "VALUES (?, ?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(conexion -> {
            PreparedStatement ps = conexion.prepareStatement(sql, new String[] {"ID"});
            ps.setString(1, pedido.getClienteNombre());
            ps.setString(2, pedido.getClienteTelefono());
            ps.setString(3, pedido.getClienteDireccion());
            ps.setBigDecimal(4, pedido.getTotal());
            ps.setString(5, pedido.getComprobante());
            ps.setString(6, pedido.getEstado());
            return ps;
        }, keyHolder);

        return keyHolder.getKey().intValue();
    }

    @Override
    public void registrarDetalle(DetallePedido detalle) {
        String sql = "INSERT INTO detalle_pedido (id_pedido, id_producto, cantidad, precio_unitario, subtotal) "
                + "VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                detalle.getIdPedido(),
                detalle.getIdProducto(),
                detalle.getCantidad(),
                detalle.getPrecioUnitario(),
                detalle.getSubtotal());
    }

    // El cliente consulta su pedido con el código y su teléfono
    @Override
    public Pedido obtenerPorIdYTelefono(int id, String telefono) {
        String sql = SELECT_BASE + "WHERE p.id = ? AND p.cliente_telefono = ?";
        List<Pedido> resultado = jdbcTemplate.query(sql, pedidoRowMapper, id, telefono);
        return resultado.isEmpty() ? null : resultado.get(0);
    }

    @Override
    public List<Pedido> listar() {
        String sql = SELECT_BASE + "ORDER BY p.fecha DESC, p.id DESC";
        return jdbcTemplate.query(sql, pedidoRowMapper);
    }

    @Override
    public List<Pedido> listarPorEstado(String estado) {
        String sql = SELECT_BASE + "WHERE p.estado = ? ORDER BY p.fecha DESC, p.id DESC";
        return jdbcTemplate.query(sql, pedidoRowMapper, estado);
    }

    @Override
    public Pedido obtenerPorId(int id) {
        String sql = SELECT_BASE + "WHERE p.id = ?";
        List<Pedido> resultado = jdbcTemplate.query(sql, pedidoRowMapper, id);
        return resultado.isEmpty() ? null : resultado.get(0);
    }

    // Solo se asigna si el pedido sigue PENDIENTE
    @Override
    public int asignarMotorizado(int idPedido, int idMotorizado) {
        String sql = "UPDATE pedido SET id_motorizado = ?, estado = 'ASIGNADO' "
                + "WHERE id = ? AND estado = 'PENDIENTE'";
        return jdbcTemplate.update(sql, idMotorizado, idPedido);
    }

    // Se reasigna si el motorizado lo rechazó o aún no lo acepta
    @Override
    public int reasignarMotorizado(int idPedido, int idMotorizado) {
        String sql = "UPDATE pedido SET id_motorizado = ?, estado = 'ASIGNADO' "
                + "WHERE id = ? AND estado IN ('RECHAZADO', 'ASIGNADO')";
        return jdbcTemplate.update(sql, idMotorizado, idPedido);
    }

    @Override
    public List<DetallePedido> listarDetalles(int idPedido) {
        String sql = "SELECT d.id, d.id_pedido, d.id_producto, pr.nombre AS producto_nombre, "
                + "d.cantidad, d.precio_unitario, d.subtotal "
                + "FROM detalle_pedido d INNER JOIN producto pr ON d.id_producto = pr.id "
                + "WHERE d.id_pedido = ? ORDER BY d.id";
        return jdbcTemplate.query(sql, detalleRowMapper, idPedido);
    }
}
