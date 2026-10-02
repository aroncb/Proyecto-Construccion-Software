<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="titulo" value="Registrar categoría" />
<%@ include file="/WEB-INF/views/header.jsp" %>

<div class="formulario">
    <h1>Registrar categoría</h1>

    <form action="${pageContext.request.contextPath}/categoria/guardar" method="post">
        <label for="nombre">Nombre</label>
        <input type="text" id="nombre" name="nombre" placeholder="Ejemplo: Bebidas" maxlength="100" required>

        <label for="descripcion">Descripción</label>
        <textarea id="descripcion" name="descripcion" placeholder="Qué tipo de productos agrupa" maxlength="255"></textarea>

        <div class="form-acciones">
            <button type="submit" class="btn">Guardar categoría</button>
            <a class="btn btn-secundario" href="${pageContext.request.contextPath}/categoria/list">Cancelar</a>
        </div>
    </form>
</div>

<%@ include file="/WEB-INF/views/footer.jsp" %>
