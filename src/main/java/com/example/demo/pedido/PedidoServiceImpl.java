package com.example.demo.pedido;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.producto.Producto;
import com.example.demo.producto.ProductoService;

// Service (clase Impl): reglas de negocio de pedidos
@Service
public class PedidoServiceImpl implements PedidoService {

    private static final int CANTIDAD_MAXIMA = 20;

    private final PedidoRepository pedidoRepository;
    private final ProductoService productoService;

    public PedidoServiceImpl(PedidoRepository pedidoRepository, ProductoService productoService) {
        this.pedidoRepository = pedidoRepository;
        this.productoService = productoService;
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
}
