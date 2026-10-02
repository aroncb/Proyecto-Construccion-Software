<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="titulo" value="Detalle de pedido" />
<%@ include file="/WEB-INF/views/header.jsp" %>

<c:if test="${pedido == null}">
    <p class="vacio">No se encontró el pedido.</p>
    <a class="btn btn-secundario" href="${pageContext.request.contextPath}/admin/pedido/list">Volver a pedidos</a>
</c:if>

<c:if test="${pedido != null}">
    <div class="encabezado">
        <h1>Pedido ${pedido.id}</h1>
        <span class="estado ${pedido.estadoClase}">${pedido.estadoAdmin}</span>
    </div>

    <%@ include file="/WEB-INF/views/admin-pedido/datos.jsp" %>

    <div class="catalogo-acciones">
        <a class="btn btn-secundario" href="${pageContext.request.contextPath}/admin/pedido/list">Volver a pedidos</a>
        <c:if test="${pedido.estado == 'PENDIENTE'}">
            <a class="btn" href="${pageContext.request.contextPath}/admin/pedido/asignar?id=${pedido.id}">Asignar motorizado</a>
        </c:if>
        <c:if test="${pedido.estado == 'RECHAZADO' || pedido.estado == 'ASIGNADO'}">
            <a class="btn" href="${pageContext.request.contextPath}/admin/pedido/reasignar?id=${pedido.id}">Reasignar motorizado</a>
        </c:if>
    </div>
</c:if>

<%@ include file="/WEB-INF/views/footer.jsp" %>
