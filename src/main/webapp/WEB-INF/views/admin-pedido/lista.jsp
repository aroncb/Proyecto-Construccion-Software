<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="titulo" value="Pedidos" />
<%@ include file="/WEB-INF/views/header.jsp" %>

<div class="encabezado">
    <h1>Pedidos</h1>
</div>

<c:set var="base" value="${pageContext.request.contextPath}/admin/pedido/list" />
<nav class="filtros">
    <a class="${estadoActual == '' ? 'activo' : ''}" href="${base}">Todos</a>
    <a class="${estadoActual == 'PENDIENTE' ? 'activo' : ''}" href="${base}?estado=PENDIENTE">Por asignar</a>
    <a class="${estadoActual == 'RECHAZADO' ? 'activo' : ''}" href="${base}?estado=RECHAZADO">Rechazados</a>
    <a class="${estadoActual == 'ASIGNADO' ? 'activo' : ''}" href="${base}?estado=ASIGNADO">Esperando aceptación</a>
    <a class="${estadoActual == 'ACEPTADO' ? 'activo' : ''}" href="${base}?estado=ACEPTADO">En camino</a>
    <a class="${estadoActual == 'ENTREGADO' ? 'activo' : ''}" href="${base}?estado=ENTREGADO">Entregados</a>
</nav>

<c:if test="${empty pedidos}">
    <p class="vacio">No hay pedidos en este estado.</p>
</c:if>

<c:if test="${not empty pedidos}">
    <div class="tabla-contenedor">
        <table>
            <tr>
                <th>Código</th>
                <th>Fecha</th>
                <th>Cliente</th>
                <th>Total</th>
                <th>Op. Yape</th>
                <th>Estado</th>
                <th>Motorizado</th>
                <th>Acciones</th>
            </tr>
            <c:forEach items="${pedidos}" var="pedido">
                <tr>
                    <td>${pedido.id}</td>
                    <td>${pedido.fechaTexto}</td>
                    <td><c:out value="${pedido.clienteNombre}" /></td>
                    <td>S/ ${pedido.total}</td>
                    <td><c:out value="${pedido.comprobante}" /></td>
                    <td><span class="estado ${pedido.estadoClase}">${pedido.estadoAdmin}</span></td>
                    <td>
                        <c:if test="${pedido.motorizadoNombre != null}"><c:out value="${pedido.motorizadoNombre}" /></c:if>
                        <c:if test="${pedido.motorizadoNombre == null}">Sin asignar</c:if>
                    </td>
                    <td class="acciones">
                        <a href="${pageContext.request.contextPath}/admin/pedido/ver?id=${pedido.id}">Ver</a>
                        <c:if test="${pedido.estado == 'PENDIENTE'}">
                            <a href="${pageContext.request.contextPath}/admin/pedido/asignar?id=${pedido.id}">Asignar</a>
                        </c:if>
                        <c:if test="${pedido.estado == 'RECHAZADO' || pedido.estado == 'ASIGNADO'}">
                            <a href="${pageContext.request.contextPath}/admin/pedido/reasignar?id=${pedido.id}">Reasignar</a>
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </div>
</c:if>

<%@ include file="/WEB-INF/views/footer.jsp" %>
