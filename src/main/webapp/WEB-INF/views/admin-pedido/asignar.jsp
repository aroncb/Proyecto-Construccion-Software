<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="titulo" value="Asignar pedido" />
<%@ include file="/WEB-INF/views/header.jsp" %>

<c:if test="${pedido == null}">
    <p class="vacio">No se encontró el pedido.</p>
    <a class="btn btn-secundario" href="${pageContext.request.contextPath}/admin/pedido/list">Volver a pedidos</a>
</c:if>

<c:if test="${pedido != null}">
    <div class="encabezado">
        <h1>Asignar pedido ${pedido.id}</h1>
        <span class="estado ${pedido.estadoClase}">${pedido.estadoAdmin}</span>
    </div>

    <%@ include file="/WEB-INF/views/admin-pedido/datos.jsp" %>

    <div class="formulario formulario-pedido">
        <c:if test="${not empty error}">
            <p class="error"><c:out value="${error}" /></p>
        </c:if>

        <c:if test="${empty motorizados}">
            <p class="vacio">No hay motorizados activos. Habilita o registra uno en Usuarios.</p>
        </c:if>

        <c:if test="${not empty motorizados}">
            <form action="${pageContext.request.contextPath}/admin/pedido/asignar" method="post">
                <input type="hidden" name="id" value="${pedido.id}">

                <label for="idMotorizado">Motorizado</label>
                <select id="idMotorizado" name="idMotorizado" required>
                    <option value="">Elige un motorizado</option>
                    <c:forEach items="${motorizados}" var="motorizado">
                        <option value="${motorizado.id}">
                            <c:out value="${motorizado.nombre} ${motorizado.apellido}" /> (${motorizado.pedidosEnCurso} en curso)
                        </option>
                    </c:forEach>
                </select>
                <p class="ayuda">Los primeros de la lista son los que tienen menos pedidos en curso.</p>

                <div class="form-acciones">
                    <button type="submit" class="btn">Asignar pedido</button>
                    <a class="btn btn-secundario" href="${pageContext.request.contextPath}/admin/pedido/list">Cancelar</a>
                </div>
            </form>
        </c:if>
    </div>
</c:if>

<%@ include file="/WEB-INF/views/footer.jsp" %>
