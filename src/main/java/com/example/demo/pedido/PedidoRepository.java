package com.example.demo.pedido;

import java.util.List;

// Repository (interface): declara los métodos de acceso a la BD
public interface PedidoRepository {

    // Inserta el pedido y devuelve el id generado
    int registrar(Pedido pedido);

    void registrarDetalle(DetallePedido detalle);

    Pedido obtenerPorIdYTelefono(int id, String telefono);

    List<DetallePedido> listarDetalles(int idPedido);

    List<Pedido> listar();

    List<Pedido> listarPorEstado(String estado);

    Pedido obtenerPorId(int id);

    // Devuelven cuántas filas se actualizaron (0 = el pedido no estaba en el estado correcto)
    int asignarMotorizado(int idPedido, int idMotorizado);

    int reasignarMotorizado(int idPedido, int idMotorizado);

    // ----- Motorizado -----

    List<Pedido> listarActivosPorMotorizado(int idMotorizado);

    List<Pedido> listarEntregadosPorMotorizado(int idMotorizado);

    // Cambia el estado solo si el pedido es de ese motorizado y está en el estado esperado
    int cambiarEstadoPorMotorizado(int idPedido, int idMotorizado, String estadoActual, String estadoNuevo);
}
