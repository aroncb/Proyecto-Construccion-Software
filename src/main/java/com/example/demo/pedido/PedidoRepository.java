package com.example.demo.pedido;

import java.util.List;

// Repository (interface): declara los métodos de acceso a la BD
public interface PedidoRepository {

    // Inserta el pedido y devuelve el id generado
    int registrar(Pedido pedido);

    void registrarDetalle(DetallePedido detalle);

    Pedido obtenerPorIdYTelefono(int id, String telefono);

    List<DetallePedido> listarDetalles(int idPedido);
}
