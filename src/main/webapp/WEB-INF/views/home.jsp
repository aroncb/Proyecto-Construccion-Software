<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="titulo" value="Inicio" />
<%@ include file="/WEB-INF/views/header.jsp" %>

<section class="bienvenida">
    <h1>Panel del administrador</h1>
    <p>Gestiona los pedidos, el catálogo y a los motorizados de Nutribubble.</p>
</section>

<section class="paneles">
    <article class="panel">
        <h2>Pedidos</h2>
        <p>Revisa los pedidos de los clientes, verifica el Yape y asígnalos a un motorizado.</p>
        <div class="panel-acciones">
            <a class="btn" href="${pageContext.request.contextPath}/admin/pedido/list?estado=PENDIENTE">Por asignar</a>
            <a class="btn btn-secundario" href="${pageContext.request.contextPath}/admin/pedido/list?estado=RECHAZADO">Rechazados</a>
            <a class="btn btn-secundario" href="${pageContext.request.contextPath}/admin/pedido/list">Todos</a>
        </div>
    </article>

    <article class="panel">
        <h2>Categorías</h2>
        <p>Organiza los productos del catálogo por tipo.</p>
        <div class="panel-acciones">
            <a class="btn" href="${pageContext.request.contextPath}/categoria/list">Ver categorías</a>
            <a class="btn btn-secundario" href="${pageContext.request.contextPath}/categoria/nuevo">Registrar categoría</a>
        </div>
    </article>

    <article class="panel">
        <h2>Productos</h2>
        <p>Registra productos, actualiza precios y controla su disponibilidad.</p>
        <div class="panel-acciones">
            <a class="btn" href="${pageContext.request.contextPath}/producto/list">Ver productos</a>
            <a class="btn btn-secundario" href="${pageContext.request.contextPath}/producto/nuevo">Registrar producto</a>
        </div>
    </article>
    <article class="panel">
        <h2>Usuarios</h2>
        <p>Registra a los motorizados y controla quién puede ingresar al sistema.</p>
        <div class="panel-acciones">
            <a class="btn" href="${pageContext.request.contextPath}/usuario/list">Ver usuarios</a>
            <a class="btn btn-secundario" href="${pageContext.request.contextPath}/usuario/nuevo">Registrar motorizado</a>
        </div>
    </article>
</section>

<%@ include file="/WEB-INF/views/footer.jsp" %>
