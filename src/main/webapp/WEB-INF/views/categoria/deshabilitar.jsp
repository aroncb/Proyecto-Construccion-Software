<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="titulo" value="Estado de categoría" />
<%@ include file="/WEB-INF/views/header.jsp" %>

<div class="formulario">
    <c:if test="${categoria == null}">
        <h1>Estado de categoría</h1>
        <p class="vacio">No se encontró la categoría. Vuelve a la lista y elige otra.</p>
        <a class="btn btn-secundario" href="${pageContext.request.contextPath}/categoria/list">Volver a categorías</a>
    </c:if>

    <c:if test="${categoria != null}">
        <h1>${categoria.activo ? 'Deshabilitar categoría' : 'Habilitar categoría'}</h1>

        <form action="${pageContext.request.contextPath}/categoria/estado" method="post">
            <input type="hidden" name="id" value="${categoria.id}">
            <input type="hidden" name="activo" value="${!categoria.activo}">

            <label>Código</label>
            <input type="text" value="${categoria.id}" readonly>

            <label>Nombre</label>
            <input type="text" value="${categoria.nombre}" readonly>

            <label>Estado actual</label>
            <input type="text" value="${categoria.activo ? 'Activa' : 'Deshabilitada'}" readonly>

            <c:if test="${categoria.activo}">
                <p class="aviso">Al deshabilitarla, ya no aparecerá al registrar productos nuevos.</p>
            </c:if>

            <div class="form-acciones">
                <button type="submit" class="btn ${categoria.activo ? 'btn-peligro' : ''}">
                    ${categoria.activo ? 'Deshabilitar' : 'Habilitar'}
                </button>
                <a class="btn btn-secundario" href="${pageContext.request.contextPath}/categoria/list">Cancelar</a>
            </div>
        </form>
    </c:if>
</div>

<%@ include file="/WEB-INF/views/footer.jsp" %>
