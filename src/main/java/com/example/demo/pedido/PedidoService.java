package com.example.demo.pedido;

import java.math.BigDecimal;
import java.util.List;

// Service (interface): declara las operaciones del negocio de pedidos
public interface PedidoService {

    // Arma el detalle con los precios reales de la BD (no se confía en el navegador)
    List<DetallePedido> armarDetalle(List<Integer> idsProducto, List<Integer> cantidades);

    BigDecimal calcularTotal(List<DetallePedido> detalles);

    // Devuelve null si los datos del cliente están bien, o el mensaje de error
    String validarDatosCliente(Pedido pedido);

    // Registra el pedido con su detalle y devuelve el código del pedido
    int registrarPedido(Pedido pedido, List<DetallePedido> detalles);

    // Busca el pedido con su detalle; null si el código y el teléfono no coinciden
    Pedido buscarParaSeguimiento(int codigo, String telefono);

    // ----- Administrador -----

    // Si estado viene vacío, lista todos
    List<Pedido> listar(String estado);

    Pedido obtenerConDetalle(int id);

    // Devuelven null si salió bien, o el mensaje de error
    String asignarMotorizado(int idPedido, Integer idMotorizado);

    String reasignarMotorizado(int idPedido, Integer idMotorizado);
}
