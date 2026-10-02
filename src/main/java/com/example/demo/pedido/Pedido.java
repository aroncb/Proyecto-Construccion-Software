package com.example.demo.pedido;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

// Modelo: representa un registro de la tabla pedido
public class Pedido {

    // Estados del pedido:
    // PENDIENTE -> ASIGNADO -> ACEPTADO -> ENTREGADO
    // Si el motorizado lo rechaza queda RECHAZADO hasta que el admin lo reasigne.
    public static final String PENDIENTE = "PENDIENTE";
    public static final String ASIGNADO = "ASIGNADO";
    public static final String ACEPTADO = "ACEPTADO";
    public static final String RECHAZADO = "RECHAZADO";
    public static final String ENTREGADO = "ENTREGADO";

    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    private Integer id;
    private String clienteNombre;
    private String clienteTelefono;
    private String clienteDireccion;
    private LocalDateTime fecha;
    private BigDecimal total;
    private String comprobante;
    private String estado;
    private Integer idMotorizado;
    private String motorizadoNombre; // viene del JOIN con usuario, solo para mostrar
    private List<DetallePedido> detalles = new ArrayList<>();

    public Pedido() {
    }

    // Paso del seguimiento que ve el cliente (1 a 4)
    public int getPaso() {
        if (estado == null) {
            return 1;
        }
        switch (estado) {
            case ASIGNADO:
                return 2;
            case ACEPTADO:
                return 3;
            case ENTREGADO:
                return 4;
            default:
                return 1; // PENDIENTE o RECHAZADO
        }
    }

    // Texto del estado para mostrar en pantalla
    public String getEstadoTexto() {
        if (estado == null) {
            return "";
        }
        switch (estado) {
            case PENDIENTE:
                return "Recibido, en espera de motorizado";
            case RECHAZADO:
                return "Reasignando motorizado";
            case ASIGNADO:
                return "Motorizado asignado";
            case ACEPTADO:
                return "En camino";
            case ENTREGADO:
                return "Entregado";
            default:
                return estado;
        }
    }

    public String getFechaTexto() {
        return fecha == null ? "" : fecha.format(FORMATO_FECHA);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getClienteNombre() {
        return clienteNombre;
    }

    public void setClienteNombre(String clienteNombre) {
        this.clienteNombre = clienteNombre;
    }

    public String getClienteTelefono() {
        return clienteTelefono;
    }

    public void setClienteTelefono(String clienteTelefono) {
        this.clienteTelefono = clienteTelefono;
    }

    public String getClienteDireccion() {
        return clienteDireccion;
    }

    public void setClienteDireccion(String clienteDireccion) {
        this.clienteDireccion = clienteDireccion;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public String getComprobante() {
        return comprobante;
    }

    public void setComprobante(String comprobante) {
        this.comprobante = comprobante;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Integer getIdMotorizado() {
        return idMotorizado;
    }

    public void setIdMotorizado(Integer idMotorizado) {
        this.idMotorizado = idMotorizado;
    }

    public String getMotorizadoNombre() {
        return motorizadoNombre;
    }

    public void setMotorizadoNombre(String motorizadoNombre) {
        this.motorizadoNombre = motorizadoNombre;
    }

    public List<DetallePedido> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetallePedido> detalles) {
        this.detalles = detalles;
    }
}
