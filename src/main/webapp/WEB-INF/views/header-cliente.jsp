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
    <a class="marca" href="${pageContext.request.contextPath}/catalogo">
        <span class="marca-burbuja"></span>Nutribubble
    </a>
    <nav class="menu">
        <a href="${pageContext.request.contextPath}/catalogo">Catálogo</a>
        <a href="${pageContext.request.contextPath}/pedido/seguimiento">Mi pedido</a>
    </nav>
    <a class="usuario" href="${pageContext.request.contextPath}/admin">Ingreso del personal</a>
</header>
<main class="contenido">
