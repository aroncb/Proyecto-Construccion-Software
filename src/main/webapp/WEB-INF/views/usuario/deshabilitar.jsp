<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="titulo" value="Estado de usuario" />
<%@ include file="/WEB-INF/views/header.jsp" %>

<div class="formulario">
    <c:if test="${usuario == null}">
        <h1>Estado de usuario</h1>
        <p class="vacio">No se encontró el usuario. Vuelve a la lista y elige otro.</p>
        <a class="btn btn-secundario" href="${pageContext.request.contextPath}/usuario/list">Volver a usuarios</a>
    </c:if>

    <c:if test="${usuario != null}">
        <h1>${usuario.activo ? 'Deshabilitar usuario' : 'Habilitar usuario'}</h1>

        <c:if test="${not empty error}">
            <p class="error"><c:out value="${error}" /></p>
        </c:if>

        <form action="${pageContext.request.contextPath}/usuario/estado" method="post">
            <input type="hidden" name="id" value="${usuario.id}">
            <input type="hidden" name="activo" value="${!usuario.activo}">

            <label>Nombre</label>
            <input type="text" value="${usuario.nombre} ${usuario.apellido}" readonly>

            <label>Usuario</label>
            <input type="text" value="${usuario.username}" readonly>

            <label>Estado actual</label>
            <input type="text" value="${usuario.activo ? 'Activo' : 'Deshabilitado'}" readonly>

            <c:if test="${usuario.activo}">
                <p class="aviso">Un motorizado deshabilitado no podrá ingresar ni recibir pedidos.</p>
            </c:if>

            <div class="form-acciones">
                <button type="submit" class="btn ${usuario.activo ? 'btn-peligro' : ''}">
                    ${usuario.activo ? 'Deshabilitar' : 'Habilitar'}
                </button>
                <a class="btn btn-secundario" href="${pageContext.request.contextPath}/usuario/list">Cancelar</a>
            </div>
        </form>
    </c:if>
</div>

<%@ include file="/WEB-INF/views/footer.jsp" %>
