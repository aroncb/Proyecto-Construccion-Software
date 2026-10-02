<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="titulo" value="Ingreso del personal" />
<%@ include file="/WEB-INF/views/header-cliente.jsp" %>

<div class="formulario">
    <h1>Ingreso del personal</h1>
    <p class="vacio">Para el administrador y los motorizados de Nutribubble.</p>

    <c:if test="${not empty error}">
        <p class="error"><c:out value="${error}" /></p>
    </c:if>

    <form action="${pageContext.request.contextPath}/login" method="post">
        <label for="username">Usuario</label>
        <input type="text" id="username" name="username" value="<c:out value='${username}' />" maxlength="50" required autofocus>

        <label for="clave">Clave</label>
        <input type="password" id="clave" name="clave" maxlength="100" required>

        <div class="form-acciones">
            <button type="submit" class="btn">Ingresar</button>
        </div>
    </form>
</div>

<%@ include file="/WEB-INF/views/footer.jsp" %>
