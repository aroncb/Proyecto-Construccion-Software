<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="titulo" value="Registrar producto" />
<%@ include file="/WEB-INF/views/header.jsp" %>

<div class="formulario">
    <h1>Registrar producto</h1>

    <c:if test="${empty categorias}">
        <p class="vacio">Primero registra una categoría activa; todo producto debe pertenecer a una.</p>
        <a class="btn" href="${pageContext.request.contextPath}/categoria/nuevo">Registrar categoría</a>
    </c:if>

    <c:if test="${not empty categorias}">
        <form action="${pageContext.request.contextPath}/producto/guardar" method="post">
            <label for="nombre">Nombre</label>
            <input type="text" id="nombre" name="nombre" placeholder="Ejemplo: Bowl de pollo" maxlength="150" required>

            <label for="descripcion">Descripción</label>
            <textarea id="descripcion" name="descripcion" placeholder="Ingredientes principales" maxlength="255"></textarea>

            <label for="precio">Precio (S/)</label>
            <input type="number" id="precio" name="precio" step="0.01" min="0.10" placeholder="0.00" required>

            <label for="idCategoria">Categoría</label>
            <select id="idCategoria" name="idCategoria" required>
                <option value="">Selecciona una categoría</option>
                <c:forEach items="${categorias}" var="categoria">
                    <option value="${categoria.id}"><c:out value="${categoria.nombre}" /></option>
                </c:forEach>
            </select>

            <label for="disponible">Disponibilidad</label>
            <select id="disponible" name="disponible">
                <option value="true">Disponible</option>
                <option value="false">No disponible</option>
            </select>

            <div class="form-acciones">
                <button type="submit" class="btn">Guardar producto</button>
                <a class="btn btn-secundario" href="${pageContext.request.contextPath}/producto/list">Cancelar</a>
            </div>
        </form>
    </c:if>
</div>

<%@ include file="/WEB-INF/views/footer.jsp" %>
