<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="titulo" value="Actualizar usuario" />
<%@ include file="/WEB-INF/views/header.jsp" %>

<div class="formulario">
    <h1>Actualizar usuario</h1>

    <c:if test="${usuario == null}">
        <p class="vacio">No se encontró el usuario. Vuelve a la lista y elige otro.</p>
        <a class="btn btn-secundario" href="${pageContext.request.contextPath}/usuario/list">Volver a usuarios</a>
    </c:if>

    <c:if test="${usuario != null}">
        <c:if test="${not empty error}">
            <p class="error"><c:out value="${error}" /></p>
        </c:if>

        <form action="${pageContext.request.contextPath}/usuario/actualizar" method="post">
            <input type="hidden" name="id" value="${usuario.id}">
            <input type="hidden" name="rol" value="${usuario.rol}">

            <label>Código</label>
            <input type="text" value="${usuario.id}" readonly>

            <label for="nombre">Nombre</label>
            <input type="text" id="nombre" name="nombre" value="${usuario.nombre}" maxlength="100" required>

            <label for="apellido">Apellido</label>
            <input type="text" id="apellido" name="apellido" value="${usuario.apellido}" maxlength="100" required>

            <label for="username">Usuario</label>
            <input type="text" id="username" name="username" value="${usuario.username}" maxlength="50" required>

            <label for="clave">Nueva clave</label>
            <input type="password" id="clave" name="clave" minlength="6" maxlength="100">
            <p class="ayuda">Déjala vacía para mantener la clave actual.</p>

            <label>Rol</label>
            <input type="text" value="${usuario.rol == 'ADMINISTRADOR' ? 'Administrador' : 'Motorizado'}" readonly>

            <div class="form-acciones">
                <button type="submit" class="btn">Guardar cambios</button>
                <a class="btn btn-secundario" href="${pageContext.request.contextPath}/usuario/list">Cancelar</a>
            </div>
        </form>
    </c:if>
</div>

<%@ include file="/WEB-INF/views/footer.jsp" %>
