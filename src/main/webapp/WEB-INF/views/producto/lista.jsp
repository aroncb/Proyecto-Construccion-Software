<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="titulo" value="Productos" />
<%@ include file="/WEB-INF/views/header.jsp" %>

<div class="encabezado">
    <h1>Productos</h1>
    <a class="btn" href="${pageContext.request.contextPath}/producto/nuevo">Registrar producto</a>
</div>

<c:if test="${empty productos}">
    <p class="vacio">Aún no hay productos. Registra el primero para que aparezca en el catálogo.</p>
</c:if>

<c:if test="${not empty productos}">
    <div class="tabla-contenedor">
        <table>
            <tr>
                <th>ID</th>
                <th>Nombre</th>
                <th>Categoría</th>
                <th>Precio</th>
                <th>Estado</th>
                <th>Acciones</th>
            </tr>
            <c:forEach items="${productos}" var="producto">
                <tr>
                    <td>${producto.id}</td>
                    <td><c:out value="${producto.nombre}" /></td>
                    <td><c:out value="${producto.categoriaNombre}" /></td>
                    <td>S/ ${producto.precio}</td>
                    <td>
                        <c:if test="${producto.disponible}"><span class="estado estado-ok">Disponible</span></c:if>
                        <c:if test="${!producto.disponible}"><span class="estado estado-off">No disponible</span></c:if>
                    </td>
                    <td class="acciones">
                        <a href="${pageContext.request.contextPath}/producto/editar?id=${producto.id}">Editar</a>
                        <a href="${pageContext.request.contextPath}/producto/disponibilidad?id=${producto.id}">Disponibilidad</a>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </div>
</c:if>

<%@ include file="/WEB-INF/views/footer.jsp" %>
