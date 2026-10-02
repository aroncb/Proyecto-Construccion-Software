<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="titulo" value="Catálogo" />
<%@ include file="/WEB-INF/views/header-cliente.jsp" %>

<section class="bienvenida">
    <h1>Comida saludable a tu puerta</h1>
    <p>Elige la cantidad de cada producto y continúa para pagar con Yape. Entregamos dentro de Huancayo.</p>
</section>

<c:if test="${not empty error}">
    <p class="error"><c:out value="${error}" /></p>
</c:if>

<c:if test="${empty productos}">
    <p class="vacio">Por ahora no hay productos disponibles. Vuelve a intentarlo más tarde.</p>
</c:if>

<c:if test="${not empty productos}">
    <form action="${pageContext.request.contextPath}/pedido/resumen" method="post">
        <div class="catalogo">
            <c:forEach items="${productos}" var="producto">
                <article class="producto-card">
                    <span class="producto-categoria"><c:out value="${producto.categoriaNombre}" /></span>
                    <h2><c:out value="${producto.nombre}" /></h2>
                    <p><c:out value="${producto.descripcion}" /></p>
                    <div class="producto-pie">
                        <strong class="precio">S/ ${producto.precio}</strong>
                        <input type="hidden" name="idProducto" value="${producto.id}">
                        <label class="cantidad">
                            Cantidad
                            <input type="number" name="cantidad" value="0" min="0" max="20">
                        </label>
                    </div>
                </article>
            </c:forEach>
        </div>

        <div class="catalogo-acciones">
            <button type="submit" class="btn">Continuar con mi pedido</button>
        </div>
    </form>
</c:if>

<%@ include file="/WEB-INF/views/footer.jsp" %>
