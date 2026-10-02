<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="titulo" value="Categorías" />
<%@ include file="/WEB-INF/views/header.jsp" %>

<div class="encabezado">
    <h1>Categorías</h1>
    <a class="btn" href="${pageContext.request.contextPath}/categoria/nuevo">Registrar categoría</a>
</div>

<c:if test="${empty categorias}">
    <p class="vacio">Aún no hay categorías. Registra la primera para empezar a armar el catálogo.</p>
</c:if>

<c:if test="${not empty categorias}">
    <div class="tabla-contenedor">
        <table>
            <tr>
                <th>ID</th>
                <th>Nombre</th>
                <th>Descripción</th>
                <th>Estado</th>
                <th>Acciones</th>
            </tr>
            <c:forEach items="${categorias}" var="categoria">
                <tr>
                    <td>${categoria.id}</td>
                    <td><c:out value="${categoria.nombre}" /></td>
                    <td><c:out value="${categoria.descripcion}" /></td>
                    <td>
                        <c:if test="${categoria.activo}"><span class="estado estado-ok">Activa</span></c:if>
                        <c:if test="${!categoria.activo}"><span class="estado estado-off">Deshabilitada</span></c:if>
                    </td>
                    <td class="acciones">
                        <a href="${pageContext.request.contextPath}/categoria/editar?id=${categoria.id}">Editar</a>
                        <a href="${pageContext.request.contextPath}/categoria/estado?id=${categoria.id}">
                            ${categoria.activo ? 'Deshabilitar' : 'Habilitar'}
                        </a>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </div>
</c:if>

<%@ include file="/WEB-INF/views/footer.jsp" %>
