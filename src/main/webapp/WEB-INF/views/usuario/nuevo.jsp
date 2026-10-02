<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="titulo" value="Registrar motorizado" />
<%@ include file="/WEB-INF/views/header.jsp" %>

<div class="formulario">
    <h1>Registrar motorizado</h1>

    <c:if test="${not empty error}">
        <p class="error"><c:out value="${error}" /></p>
    </c:if>

    <form action="${pageContext.request.contextPath}/usuario/guardar" method="post">
        <label for="nombre">Nombre</label>
        <input type="text" id="nombre" name="nombre" value="${usuario.nombre}" maxlength="100" required>

        <label for="apellido">Apellido</label>
        <input type="text" id="apellido" name="apellido" value="${usuario.apellido}" maxlength="100" required>

        <label for="username">Usuario</label>
        <input type="text" id="username" name="username" value="${usuario.username}" placeholder="Ejemplo: lgomez" maxlength="50" required>

        <label for="clave">Clave</label>
        <input type="password" id="clave" name="clave" minlength="6" maxlength="100" required>
        <p class="ayuda">Mínimo 6 caracteres.</p>

        <label>Rol</label>
        <input type="text" value="Motorizado" readonly>

        <div class="form-acciones">
            <button type="submit" class="btn">Registrar motorizado</button>
            <a class="btn btn-secundario" href="${pageContext.request.contextPath}/usuario/list">Cancelar</a>
        </div>
    </form>
</div>

<%@ include file="/WEB-INF/views/footer.jsp" %>
