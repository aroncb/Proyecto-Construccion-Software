<%@ page pageEncoding="UTF-8" %>
<div class="pedido-datos">
    <p><b>Fecha:</b> ${pedido.fechaTexto}</p>
    <p><b>Cliente:</b> <c:out value="${pedido.clienteNombre}" /> (cel. <c:out value="${pedido.clienteTelefono}" />)</p>
    <p><b>Dirección:</b> <c:out value="${pedido.clienteDireccion}" /></p>
    <p><b>N.° de operación Yape:</b> <c:out value="${pedido.comprobante}" /></p>
    <p><b>Motorizado:</b>
        <c:if test="${pedido.motorizadoNombre != null}"><c:out value="${pedido.motorizadoNombre}" /></c:if>
        <c:if test="${pedido.motorizadoNombre == null}">Sin asignar</c:if>
    </p>
</div>

<div class="tabla-contenedor">
    <table>
        <tr>
            <th>Producto</th>
            <th>Cantidad</th>
            <th>Precio</th>
            <th>Subtotal</th>
        </tr>
        <c:forEach items="${pedido.detalles}" var="detalle">
            <tr>
                <td><c:out value="${detalle.productoNombre}" /></td>
                <td>${detalle.cantidad}</td>
                <td>S/ ${detalle.precioUnitario}</td>
                <td>S/ ${detalle.subtotal}</td>
            </tr>
        </c:forEach>
        <tr class="fila-total">
            <td colspan="3">Total</td>
            <td>S/ ${pedido.total}</td>
        </tr>
    </table>
</div>
