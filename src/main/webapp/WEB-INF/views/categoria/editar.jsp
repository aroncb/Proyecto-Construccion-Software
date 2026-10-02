<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="titulo" value="Actualizar categoría" />
<%@ include file="/WEB-INF/views/header.jsp" %>

<div class="formulario">
    <h1>Actualizar categoría</h1>

    <c:if test="${categoria == null}">
        <p class="vacio">No se encontró la categoría. Vuelve a la lista y elige otra.</p>
        <a class="btn btn-secundario" href="${pageContext.request.contextPath}/categoria/list">Volver a categorías</a>
    </c:if>

    <c:if test="${categoria != null}">
        <form action="${pageContext.request.contextPath}/categoria/actualizar" method="post">
            <input type="hidden" name="id" value="${categoria.id}">

            <label>Código</label>
            <input type="text" value="${categoria.id}" readonly>

            <label for="nombre">Nombre</label>
            <input type="text" id="nombre" name="nombre" value="${categoria.nombre}" maxlength="100" required>

            <label for="descripcion">Descripción</label>
            <textarea id="descripcion" name="descripcion" maxlength="255">${categoria.descripcion}</textarea>

            <div class="form-acciones">
                <button type="submit" class="btn">Guardar cambios</button>
                <a class="btn btn-secundario" href="${pageContext.request.contextPath}/categoria/list">Cancelar</a>
            </div>
        </form>
    </c:if>
</div>

<%@ include file="/WEB-INF/views/footer.jsp" %>
