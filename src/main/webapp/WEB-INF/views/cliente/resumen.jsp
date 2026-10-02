<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="titulo" value="Confirmar pedido" />
<%@ include file="/WEB-INF/views/header-cliente.jsp" %>

<div class="encabezado">
    <h1>Confirma tu pedido</h1>
    <a class="btn btn-secundario" href="${pageContext.request.contextPath}/catalogo">Volver al catálogo</a>
</div>

<div class="tabla-contenedor">
    <table>
        <tr>
            <th>Producto</th>
            <th>Cantidad</th>
            <th>Precio</th>
            <th>Subtotal</th>
        </tr>
        <c:forEach items="${detalles}" var="detalle">
            <tr>
                <td><c:out value="${detalle.productoNombre}" /></td>
                <td>${detalle.cantidad}</td>
                <td>S/ ${detalle.precioUnitario}</td>
                <td>S/ ${detalle.subtotal}</td>
            </tr>
        </c:forEach>
        <tr class="fila-total">
            <td colspan="3">Total a pagar</td>
            <td>S/ ${total}</td>
        </tr>
    </table>
</div>

<div class="formulario formulario-pedido">
    <div class="yape">
        <strong>Paga con Yape</strong>
        <p>Yapea <b>S/ ${total}</b> al número <b>${numeroYape}</b> (Nutribubble) y escribe abajo el número de operación que te muestra Yape.</p>
    </div>

    <c:if test="${not empty error}">
        <p class="error"><c:out value="${error}" /></p>
    </c:if>

    <form action="${pageContext.request.contextPath}/pedido/registrar" method="post">
        <c:forEach items="${detalles}" var="detalle">
            <input type="hidden" name="idProducto" value="${detalle.idProducto}">
            <input type="hidden" name="cantidad" value="${detalle.cantidad}">
        </c:forEach>

        <label for="clienteNombre">Nombre completo</label>
        <input type="text" id="clienteNombre" name="clienteNombre" value="${pedido.clienteNombre}" maxlength="150" required>

        <label for="clienteTelefono">Celular</label>
        <input type="tel" id="clienteTelefono" name="clienteTelefono" value="${pedido.clienteTelefono}"
               pattern="9[0-9]{8}" maxlength="9" placeholder="9XXXXXXXX" required>
        <p class="ayuda">Con este número y tu código podrás seguir tu pedido.</p>

        <label for="clienteDireccion">Dirección de entrega</label>
        <input type="text" id="clienteDireccion" name="clienteDireccion" value="${pedido.clienteDireccion}"
               maxlength="255" placeholder="Calle, número y referencia" required>

        <label for="comprobante">N.° de operación Yape</label>
        <input type="text" id="comprobante" name="comprobante" value="${pedido.comprobante}"
               pattern="[0-9]{4,20}" maxlength="20" inputmode="numeric" required>

        <div class="form-acciones">
            <button type="submit" class="btn">Confirmar pedido</button>
        </div>
    </form>
</div>

<%@ include file="/WEB-INF/views/footer.jsp" %>
