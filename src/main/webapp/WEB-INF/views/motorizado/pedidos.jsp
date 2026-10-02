<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="titulo" value="Mis pedidos" />
<%@ include file="/WEB-INF/views/header-motorizado.jsp" %>

<div class="encabezado">
    <h1>Mis pedidos</h1>
    <a class="btn btn-secundario" href="${pageContext.request.contextPath}/motorizado/pedidos">Actualizar</a>
</div>

<c:if test="${empty pedidos}">
    <p class="vacio">No tienes pedidos por ahora. Cuando el administrador te asigne uno, aparecerá aquí.</p>
</c:if>

<c:if test="${not empty pedidos}">
    <div class="entregas">
        <c:forEach items="${pedidos}" var="pedido">
            <article class="entrega ${pedido.estado == 'ACEPTADO' ? 'entrega-en-camino' : ''}">
                <div class="entrega-cabecera">
                    <h2>Pedido ${pedido.id}</h2>
                    <span class="estado ${pedido.estadoClase}">${pedido.estadoAdmin}</span>
                </div>
                <p><b><c:out value="${pedido.clienteNombre}" /></b> (cel. <c:out value="${pedido.clienteTelefono}" />)</p>
                <p><c:out value="${pedido.clienteDireccion}" /></p>
                <p class="entrega-total">S/ ${pedido.total}</p>

                <div class="panel-acciones">
                    <c:if test="${pedido.estado == 'ASIGNADO'}">
                        <a class="btn" href="${pageContext.request.contextPath}/motorizado/aceptar?id=${pedido.id}">Aceptar</a>
                        <a class="btn btn-secundario" href="${pageContext.request.contextPath}/motorizado/rechazar?id=${pedido.id}">Rechazar</a>
                    </c:if>
                    <c:if test="${pedido.estado == 'ACEPTADO'}">
                        <a class="btn" href="${pageContext.request.contextPath}/motorizado/entregar?id=${pedido.id}">Confirmar entrega</a>
                    </c:if>
                </div>
            </article>
        </c:forEach>
    </div>
</c:if>

<c:if test="${not empty entregados}">
    <h2 class="subtitulo">Entregados</h2>
    <div class="tabla-contenedor">
        <table>
            <tr>
                <th>Código</th>
                <th>Fecha</th>
                <th>Cliente</th>
                <th>Total</th>
            </tr>
            <c:forEach items="${entregados}" var="pedido">
                <tr>
                    <td>${pedido.id}</td>
                    <td>${pedido.fechaTexto}</td>
                    <td><c:out value="${pedido.clienteNombre}" /></td>
                    <td>S/ ${pedido.total}</td>
                </tr>
            </c:forEach>
        </table>
    </div>
</c:if>

<%@ include file="/WEB-INF/views/footer.jsp" %>
