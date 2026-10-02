<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="titulo" value="Mi pedido" />
<%@ include file="/WEB-INF/views/header-cliente.jsp" %>

<c:if test="${nuevo}">
    <div class="exito">
        <strong>¡Pedido registrado!</strong>
        Tu código es <b>${pedido.id}</b>. Guárdalo junto con tu celular para seguir tu pedido.
    </div>
</c:if>

<c:if test="${pedido == null}">
    <div class="formulario">
        <h1>Sigue tu pedido</h1>

        <c:if test="${not empty error}">
            <p class="error"><c:out value="${error}" /></p>
        </c:if>

        <form action="${pageContext.request.contextPath}/pedido/seguimiento" method="get">
            <label for="codigo">Código del pedido</label>
            <input type="number" id="codigo" name="codigo" value="${codigo}" min="1" required>

            <label for="telefono">Celular con el que pediste</label>
            <input type="tel" id="telefono" name="telefono" value="<c:out value='${telefono}' />"
                   pattern="9[0-9]{8}" maxlength="9" required>

            <div class="form-acciones">
                <button type="submit" class="btn">Ver mi pedido</button>
            </div>
        </form>
    </div>
</c:if>

<c:if test="${pedido != null}">
    <div class="encabezado">
        <h1>Pedido ${pedido.id}</h1>
        <span class="estado ${pedido.estado == 'ENTREGADO' ? 'estado-ok' : 'estado-proceso'}">${pedido.estadoTexto}</span>
    </div>

    <ol class="seguimiento">
        <li class="${pedido.paso >= 1 ? 'hecho' : ''}">Recibido</li>
        <li class="${pedido.paso >= 2 ? 'hecho' : ''}">Motorizado asignado</li>
        <li class="${pedido.paso >= 3 ? 'hecho' : ''}">En camino</li>
        <li class="${pedido.paso >= 4 ? 'hecho' : ''}">Entregado</li>
    </ol>

    <div class="pedido-datos">
        <p><b>Fecha:</b> ${pedido.fechaTexto}</p>
        <p><b>Entregar a:</b> <c:out value="${pedido.clienteNombre}" /></p>
        <p><b>Dirección:</b> <c:out value="${pedido.clienteDireccion}" /></p>
        <c:if test="${pedido.motorizadoNombre != null}">
            <p><b>Motorizado:</b> <c:out value="${pedido.motorizadoNombre}" /></p>
        </c:if>
    </div>

    <div class="tabla-contenedor">
        <table>
            <tr>
                <th>Producto</th>
                <th>Cantidad</th>
                <th>Subtotal</th>
            </tr>
            <c:forEach items="${pedido.detalles}" var="detalle">
                <tr>
                    <td><c:out value="${detalle.productoNombre}" /></td>
                    <td>${detalle.cantidad}</td>
                    <td>S/ ${detalle.subtotal}</td>
                </tr>
            </c:forEach>
            <tr class="fila-total">
                <td colspan="2">Total pagado</td>
                <td>S/ ${pedido.total}</td>
            </tr>
        </table>
    </div>

    <div class="catalogo-acciones">
        <a class="btn btn-secundario" href="${pageContext.request.contextPath}/pedido/seguimiento?codigo=${pedido.id}&telefono=${pedido.clienteTelefono}">Actualizar estado</a>
        <a class="btn" href="${pageContext.request.contextPath}/catalogo">Hacer otro pedido</a>
    </div>
</c:if>

<%@ include file="/WEB-INF/views/footer.jsp" %>
