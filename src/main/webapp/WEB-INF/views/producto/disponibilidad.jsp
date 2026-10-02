<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="titulo" value="Disponibilidad de producto" />
<%@ include file="/WEB-INF/views/header.jsp" %>

<div class="formulario">
    <h1>Disponibilidad de producto</h1>

    <c:if test="${producto == null}">
        <p class="vacio">No se encontró el producto. Vuelve a la lista y elige otro.</p>
        <a class="btn btn-secundario" href="${pageContext.request.contextPath}/producto/list">Volver a productos</a>
    </c:if>

    <c:if test="${producto != null}">
        <form action="${pageContext.request.contextPath}/producto/disponibilidad" method="post">
            <input type="hidden" name="id" value="${producto.id}">

            <label>Código</label>
            <input type="text" value="${producto.id}" readonly>

            <label>Nombre</label>
            <input type="text" value="${producto.nombre}" readonly>

            <label>Estado actual</label>
            <input type="text" value="${producto.disponible ? 'Disponible' : 'No disponible'}" readonly>

            <label for="disponible">Nuevo estado</label>
            <select id="disponible" name="disponible">
                <option value="true" ${producto.disponible ? 'selected' : ''}>Disponible</option>
                <option value="false" ${!producto.disponible ? 'selected' : ''}>No disponible</option>
            </select>

            <div class="form-acciones">
                <button type="submit" class="btn">Guardar estado</button>
                <a class="btn btn-secundario" href="${pageContext.request.contextPath}/producto/list">Cancelar</a>
            </div>
        </form>
    </c:if>
</div>

<%@ include file="/WEB-INF/views/footer.jsp" %>
