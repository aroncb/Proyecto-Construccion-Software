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
    <a class="marca" href="${pageContext.request.contextPath}/motorizado/pedidos">
        <span class="marca-burbuja"></span>Nutribubble
    </a>
    <nav class="menu">
        <a href="${pageContext.request.contextPath}/motorizado/pedidos">Mis pedidos</a>
    </nav>
    <div class="sesion">
        <span class="usuario">${sessionScope.usuarioSesion.nombre} ${sessionScope.usuarioSesion.apellido}</span>
        <a href="${pageContext.request.contextPath}/logout">Cerrar sesión</a>
    </div>
</header>
<main class="contenido">
