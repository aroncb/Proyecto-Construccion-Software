package com.example.demo.pedido;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.producto.Producto;
import com.example.demo.producto.ProductoService;
import com.example.demo.usuario.Usuario;
import com.example.demo.usuario.UsuarioService;

// Service (clase Impl): reglas de negocio de pedidos
@Service
public class PedidoServiceImpl implements PedidoService {

    private static final int CANTIDAD_MAXIMA = 20;

    private final PedidoRepository pedidoRepository;
    private final ProductoService productoService;
    private final UsuarioService usuarioService;

    public PedidoServiceImpl(PedidoRepository pedidoRepository, ProductoService productoService,
                             UsuarioService usuarioService) {
        this.pedidoRepository = pedidoRepository;
        this.productoService = productoService;
        this.usuarioService = usuarioService;
    }

    @Override
    public List<DetallePedido> armarDetalle(List<Integer> idsProducto, List<Integer> cantidades) {
        List<DetallePedido> detalles = new ArrayList<>();
        if (idsProducto == null || cantidades == null) {
            return detalles;
        }

        int filas = Math.min(idsProducto.size(), cantidades.size());
        for (int i = 0; i < filas; i++) {
            Integer idProducto = idsProducto.get(i);
            Integer cantidad = cantidades.get(i);

            // Se ignoran los productos con cantidad 0 o vacía
            if (idProducto == null || cantidad == null || cantidad <= 0) {
                continue;
            }
            if (cantidad > CANTIDAD_MAXIMA) {
                cantidad = CANTIDAD_MAXIMA;
            }

            // Regla: solo se venden productos que existen y están disponibles
            Producto producto = productoService.obtenerPorId(idProducto);
            if (producto == null || !producto.getDisponible()) {
                continue;
            }

            DetallePedido detalle = new DetallePedido();
            detalle.setIdProducto(producto.getId());
            detalle.setProductoNombre(producto.getNombre());
            detalle.setCantidad(cantidad);
            detalle.setPrecioUnitario(producto.getPrecio());
            detalle.setSubtotal(producto.getPrecio().multiply(BigDecimal.valueOf(cantidad)));
            detalles.add(detalle);
        }
        return detalles;
    }

    @Override
    public BigDecimal calcularTotal(List<DetallePedido> detalles) {
        BigDecimal total = BigDecimal.ZERO;
        for (DetallePedido detalle : detalles) {
            total = total.add(detalle.getSubtotal());
        }
        return total;
    }

    @Override
    public String validarDatosCliente(Pedido pedido) {
        if (pedido.getClienteNombre() == null || pedido.getClienteNombre().isBlank()) {
            return "Escribe tu nombre para saber a quién entregar el pedido.";
        }
        if (pedido.getClienteTelefono() == null || !pedido.getClienteTelefono().trim().matches("9\\d{8}")) {
            return "El celular debe tener 9 dígitos y empezar con 9.";
        }
        if (pedido.getClienteDireccion() == null || pedido.getClienteDireccion().isBlank()) {
            return "Escribe la dirección de entrega.";
        }
        if (pedido.getComprobante() == null || !pedido.getComprobante().trim().matches("\\d{4,20}")) {
            return "Escribe el número de operación de tu Yape (solo números).";
        }
        return null;
    }

    // @Transactional: si falla algún detalle, no se guarda nada del pedido
    @Override
    @Transactional
    public int registrarPedido(Pedido pedido, List<DetallePedido> detalles) {
        pedido.setClienteNombre(pedido.getClienteNombre().trim());
        pedido.setClienteTelefono(pedido.getClienteTelefono().trim());
        pedido.setClienteDireccion(pedido.getClienteDireccion().trim());
        pedido.setComprobante(pedido.getComprobante().trim());
        pedido.setTotal(calcularTotal(detalles));
        pedido.setEstado(Pedido.PENDIENTE);

        int idPedido = pedidoRepository.registrar(pedido);

        for (DetallePedido detalle : detalles) {
            detalle.setIdPedido(idPedido);
            pedidoRepository.registrarDetalle(detalle);
        }
        return idPedido;
    }

    @Override
    public Pedido buscarParaSeguimiento(int codigo, String telefono) {
        Pedido pedido = pedidoRepository.obtenerPorIdYTelefono(codigo, telefono.trim());
        if (pedido != null) {
            pedido.setDetalles(pedidoRepository.listarDetalles(pedido.getId()));
        }
        return pedido;
    }

    // ----- Administrador -----

    @Override
    public List<Pedido> listar(String estado) {
        if (estado == null || estado.isBlank()) {
            return pedidoRepository.listar();
        }
        return pedidoRepository.listarPorEstado(estado);
    }

    @Override
    public Pedido obtenerConDetalle(int id) {
        Pedido pedido = pedidoRepository.obtenerPorId(id);
        if (pedido != null) {
            pedido.setDetalles(pedidoRepository.listarDetalles(id));
        }
        return pedido;
    }

    // Regla: solo se asigna un pedido PENDIENTE a un motorizado activo
    @Override
    public String asignarMotorizado(int idPedido, Integer idMotorizado) {
        String error = validarMotorizado(idMotorizado);
        if (error != null) {
            return error;
        }
        int filas = pedidoRepository.asignarMotorizado(idPedido, idMotorizado);
        if (filas == 0) {
            return "Este pedido ya no está pendiente; revisa su estado actual.";
        }
        return null;
    }

    // Regla: se reasigna si el motorizado lo rechazó o no lo acepta (enfermo, sin batería, etc.)
    // y debe ir a un motorizado distinto al actual
    @Override
    public String reasignarMotorizado(int idPedido, Integer idMotorizado) {
        String error = validarMotorizado(idMotorizado);
        if (error != null) {
            return error;
        }
        Pedido pedido = pedidoRepository.obtenerPorId(idPedido);
        if (pedido == null) {
            return "No se encontró el pedido.";
        }
        if (idMotorizado.equals(pedido.getIdMotorizado())) {
            return "Elige un motorizado distinto al que tiene el pedido ahora.";
        }
        int filas = pedidoRepository.reasignarMotorizado(idPedido, idMotorizado);
        if (filas == 0) {
            return "Solo se reasignan pedidos rechazados o que aún no han sido aceptados.";
        }
        return null;
    }

    // ----- Motorizado -----

    @Override
    public List<Pedido> listarActivosDeMotorizado(int idMotorizado) {
        return pedidoRepository.listarActivosPorMotorizado(idMotorizado);
    }

    @Override
    public List<Pedido> listarEntregadosDeMotorizado(int idMotorizado) {
        return pedidoRepository.listarEntregadosPorMotorizado(idMotorizado);
    }

    // Regla: un motorizado solo ve los pedidos que le asignaron
    @Override
    public Pedido obtenerParaMotorizado(int idPedido, int idMotorizado) {
        Pedido pedido = obtenerConDetalle(idPedido);
        if (pedido == null || pedido.getIdMotorizado() == null || pedido.getIdMotorizado() != idMotorizado) {
            return null;
        }
        return pedido;
    }

    // ASIGNADO -> ACEPTADO
    @Override
    public String aceptarPedido(int idPedido, int idMotorizado) {
        int filas = pedidoRepository.cambiarEstadoPorMotorizado(idPedido, idMotorizado, Pedido.ASIGNADO, Pedido.ACEPTADO);
        return filas == 0 ? "Este pedido ya no está esperando tu respuesta." : null;
    }

    // ASIGNADO -> RECHAZADO (se guarda quién lo rechazó para que el admin lo reasigne a otro)
    @Override
    public String rechazarPedido(int idPedido, int idMotorizado) {
        int filas = pedidoRepository.cambiarEstadoPorMotorizado(idPedido, idMotorizado, Pedido.ASIGNADO, Pedido.RECHAZADO);
        return filas == 0 ? "Este pedido ya no está esperando tu respuesta." : null;
    }

    // ACEPTADO -> ENTREGADO
    @Override
    public String confirmarEntrega(int idPedido, int idMotorizado) {
        int filas = pedidoRepository.cambiarEstadoPorMotorizado(idPedido, idMotorizado, Pedido.ACEPTADO, Pedido.ENTREGADO);
        return filas == 0 ? "Solo puedes entregar un pedido que aceptaste." : null;
    }

    private String validarMotorizado(Integer idMotorizado) {
        if (idMotorizado == null) {
            return "Elige un motorizado.";
        }
        Usuario motorizado = usuarioService.obtenerPorId(idMotorizado);
        if (motorizado == null || !"MOTORIZADO".equals(motorizado.getRol()) || !motorizado.getActivo()) {
            return "El motorizado elegido no está activo.";
        }
        return null;
    }
}
