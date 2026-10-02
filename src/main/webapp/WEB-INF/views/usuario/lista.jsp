<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="titulo" value="Usuarios" />
<%@ include file="/WEB-INF/views/header.jsp" %>

<div class="encabezado">
    <h1>Usuarios</h1>
    <a class="btn" href="${pageContext.request.contextPath}/usuario/nuevo">Registrar motorizado</a>
</div>

<c:if test="${empty usuarios}">
    <p class="vacio">No hay usuarios registrados.</p>
</c:if>

<c:if test="${not empty usuarios}">
    <div class="tabla-contenedor">
        <table>
            <tr>
                <th>ID</th>
                <th>Nombre</th>
                <th>Usuario</th>
                <th>Rol</th>
                <th>Estado</th>
                <th>Acciones</th>
            </tr>
            <c:forEach items="${usuarios}" var="usuario">
                <tr>
                    <td>${usuario.id}</td>
                    <td><c:out value="${usuario.nombre} ${usuario.apellido}" /></td>
                    <td><c:out value="${usuario.username}" /></td>
                    <td>${usuario.rol == 'ADMINISTRADOR' ? 'Administrador' : 'Motorizado'}</td>
                    <td>
                        <c:if test="${usuario.activo}"><span class="estado estado-ok">Activo</span></c:if>
                        <c:if test="${!usuario.activo}"><span class="estado estado-off">Deshabilitado</span></c:if>
                    </td>
                    <td class="acciones">
                        <a href="${pageContext.request.contextPath}/usuario/editar?id=${usuario.id}">Editar</a>
                        <c:if test="${usuario.rol != 'ADMINISTRADOR'}">
                            <a href="${pageContext.request.contextPath}/usuario/estado?id=${usuario.id}">
                                ${usuario.activo ? 'Deshabilitar' : 'Habilitar'}
                            </a>
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </div>
</c:if>

<%@ include file="/WEB-INF/views/footer.jsp" %>
