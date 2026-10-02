<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="titulo" value="Rechazar pedido" />
<%@ include file="/WEB-INF/views/header-motorizado.jsp" %>

<c:if test="${pedido == null}">
    <h1>Rechazar pedido</h1>
    <p class="vacio">Este pedido no está asignado a ti.</p>
    <a class="btn btn-secundario" href="${pageContext.request.contextPath}/motorizado/pedidos">Volver a mis pedidos</a>
</c:if>

<c:if test="${pedido != null}">
    <div class="encabezado">
        <h1>Rechazar pedido ${pedido.id}</h1>
        <span class="estado ${pedido.estadoClase}">${pedido.estadoAdmin}</span>
    </div>

    <c:if test="${not empty error}">
        <p class="error"><c:out value="${error}" /></p>
    </c:if>

    <p class="aviso">Si no puedes llevarlo, recházalo para que el administrador lo asigne a otro motorizado.</p>

    <%@ include file="/WEB-INF/views/admin-pedido/datos.jsp" %>

    <form action="${pageContext.request.contextPath}/motorizado/rechazar" method="post" class="catalogo-acciones">
        <input type="hidden" name="id" value="${pedido.id}">
        <a class="btn btn-secundario" href="${pageContext.request.contextPath}/motorizado/pedidos">Volver</a>
        <button type="submit" class="btn btn-peligro">Rechazar pedido</button>
    </form>
</c:if>

<%@ include file="/WEB-INF/views/footer.jsp" %>
