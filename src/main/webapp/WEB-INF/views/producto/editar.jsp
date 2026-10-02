<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="titulo" value="Actualizar producto" />
<%@ include file="/WEB-INF/views/header.jsp" %>

<div class="formulario">
    <h1>Actualizar producto</h1>

    <c:if test="${producto == null}">
        <p class="vacio">No se encontró el producto. Vuelve a la lista y elige otro.</p>
        <a class="btn btn-secundario" href="${pageContext.request.contextPath}/producto/list">Volver a productos</a>
    </c:if>

    <c:if test="${producto != null}">
        <form action="${pageContext.request.contextPath}/producto/actualizar" method="post">
            <input type="hidden" name="id" value="${producto.id}">

            <label>Código</label>
            <input type="text" value="${producto.id}" readonly>

            <label for="nombre">Nombre</label>
            <input type="text" id="nombre" name="nombre" value="${producto.nombre}" maxlength="150" required>

            <label for="descripcion">Descripción</label>
            <textarea id="descripcion" name="descripcion" maxlength="255">${producto.descripcion}</textarea>

            <label for="precio">Precio (S/)</label>
            <input type="number" id="precio" name="precio" step="0.01" min="0.10" value="${producto.precio}" required>

            <label for="idCategoria">Categoría</label>
            <select id="idCategoria" name="idCategoria" required>
                <c:forEach items="${categorias}" var="categoria">
                    <option value="${categoria.id}" ${categoria.id == producto.idCategoria ? 'selected' : ''}>
                        <c:out value="${categoria.nombre}" />
                    </option>
                </c:forEach>
            </select>

            <p class="aviso">La disponibilidad se cambia desde su propia opción en la lista de productos.</p>

            <div class="form-acciones">
                <button type="submit" class="btn">Guardar cambios</button>
                <a class="btn btn-secundario" href="${pageContext.request.contextPath}/producto/list">Cancelar</a>
            </div>
        </form>
    </c:if>
</div>

<%@ include file="/WEB-INF/views/footer.jsp" %>
