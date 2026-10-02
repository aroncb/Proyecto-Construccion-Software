<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="titulo" value="Reasignar pedido" />
<%@ include file="/WEB-INF/views/header.jsp" %>

<c:if test="${pedido == null}">
    <p class="vacio">No se encontró el pedido.</p>
    <a class="btn btn-secundario" href="${pageContext.request.contextPath}/admin/pedido/list">Volver a pedidos</a>
</c:if>

<c:if test="${pedido != null}">
    <div class="encabezado">
        <h1>Reasignar pedido ${pedido.id}</h1>
        <span class="estado ${pedido.estadoClase}">${pedido.estadoAdmin}</span>
    </div>

    <c:if test="${pedido.estado == 'RECHAZADO'}">
        <p class="aviso">El motorizado <c:out value="${pedido.motorizadoNombre}" /> rechazó este pedido. Asígnalo a otro motorizado.</p>
    </c:if>
    <c:if test="${pedido.estado == 'ASIGNADO'}">
        <p class="aviso"><c:out value="${pedido.motorizadoNombre}" /> todavía no acepta este pedido. Si no responde, pásalo a otro motorizado.</p>
    </c:if>

    <%@ include file="/WEB-INF/views/admin-pedido/datos.jsp" %>

    <div class="formulario formulario-pedido">
        <c:if test="${not empty error}">
            <p class="error"><c:out value="${error}" /></p>
        </c:if>

        <form action="${pageContext.request.contextPath}/admin/pedido/reasignar" method="post">
            <input type="hidden" name="id" value="${pedido.id}">

            <label for="idMotorizado">Nuevo motorizado</label>
            <select id="idMotorizado" name="idMotorizado" required>
                <option value="">Elige un motorizado</option>
                <c:forEach items="${motorizados}" var="motorizado">
                    <c:if test="${motorizado.id != pedido.idMotorizado}">
                        <option value="${motorizado.id}">
                            <c:out value="${motorizado.nombre} ${motorizado.apellido}" /> (${motorizado.pedidosEnCurso} en curso)
                        </option>
                    </c:if>
                </c:forEach>
            </select>
            <p class="ayuda">No aparece el motorizado que tiene el pedido ahora.</p>

            <div class="form-acciones">
                <button type="submit" class="btn">Reasignar pedido</button>
                <a class="btn btn-secundario" href="${pageContext.request.contextPath}/admin/pedido/list">Cancelar</a>
            </div>
        </form>
    </div>
</c:if>

<%@ include file="/WEB-INF/views/footer.jsp" %>
