<%@ page pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>${titulo} | Nutribubble</title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link href="https://fonts.googleapis.com/css2?family=Nunito:wght@400;600;800&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/nutribubble.css">
</head>
<body>
<header class="navbar">
    <a class="marca" href="${pageContext.request.contextPath}/admin">
        <span class="marca-burbuja"></span>Nutribubble
    </a>
    <nav class="menu">
        <a href="${pageContext.request.contextPath}/admin">Inicio</a>
        <a href="${pageContext.request.contextPath}/categoria/list">Categorías</a>
        <a href="${pageContext.request.contextPath}/producto/list">Productos</a>
        <a href="${pageContext.request.contextPath}/admin/pedido/list">Pedidos</a>
        <a href="${pageContext.request.contextPath}/usuario/list">Usuarios</a>
    </nav>
    <span class="usuario">Usuario: Administrador</span>
</header>
<main class="contenido">
