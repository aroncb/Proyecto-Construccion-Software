-- =========================================
-- NUTRIBUBBLE - Datos de prueba
-- =========================================

-- Categorías (id 1, 2, 3)
INSERT INTO categoria (nombre, descripcion, activo) VALUES ('Bowls', 'Bowls con proteína, granos y verduras', TRUE);
INSERT INTO categoria (nombre, descripcion, activo) VALUES ('Bebidas', 'Jugos y bebidas naturales', TRUE);
INSERT INTO categoria (nombre, descripcion, activo) VALUES ('Acompañamientos', 'Complementos saludables', TRUE);

-- Productos
INSERT INTO producto (nombre, descripcion, precio, disponible, id_categoria) VALUES ('Bowl de pollo', 'Pollo a la plancha con arroz integral y verduras', 18.00, TRUE, 1);
INSERT INTO producto (nombre, descripcion, precio, disponible, id_categoria) VALUES ('Bowl de quinua', 'Quinua, palta, garbanzos y vegetales frescos', 16.50, TRUE, 1);
INSERT INTO producto (nombre, descripcion, precio, disponible, id_categoria) VALUES ('Jugo verde', 'Espinaca, piña, pepino y kion', 8.00, TRUE, 2);
INSERT INTO producto (nombre, descripcion, precio, disponible, id_categoria) VALUES ('Limonada de chía', 'Limonada natural con semillas de chía', 6.00, TRUE, 2);
INSERT INTO producto (nombre, descripcion, precio, disponible, id_categoria) VALUES ('Ensalada fresca', 'Lechuga, tomate, zanahoria y vinagreta', 12.00, TRUE, 3);
INSERT INTO producto (nombre, descripcion, precio, disponible, id_categoria) VALUES ('Wrap integral', 'Tortilla integral con pollo y vegetales', 10.00, FALSE, 3);

-- Usuarios: 1 administrador y 5 motorizados
INSERT INTO usuario (nombre, apellido, username, clave, rol, activo) VALUES ('Carlos', 'Pérez', 'admin', 'admin123', 'ADMINISTRADOR', TRUE);
INSERT INTO usuario (nombre, apellido, username, clave, rol, activo) VALUES ('Luis', 'Gómez', 'lgomez', '123456', 'MOTORIZADO', TRUE);
INSERT INTO usuario (nombre, apellido, username, clave, rol, activo) VALUES ('Pedro', 'Díaz', 'pdiaz', '123456', 'MOTORIZADO', TRUE);
INSERT INTO usuario (nombre, apellido, username, clave, rol, activo) VALUES ('José', 'Rojas', 'jrojas', '123456', 'MOTORIZADO', TRUE);
INSERT INTO usuario (nombre, apellido, username, clave, rol, activo) VALUES ('Alberto', 'Reyes', 'areyes', '123456', 'MOTORIZADO', TRUE);
INSERT INTO usuario (nombre, apellido, username, clave, rol, activo) VALUES ('Hugo', 'Torres', 'htorres', '123456', 'MOTORIZADO', TRUE);

-- Pedidos de prueba (código 1: pendiente / código 2: en camino con Luis Gómez)
INSERT INTO pedido (cliente_nombre, cliente_telefono, cliente_direccion, total, comprobante, estado, id_motorizado)
VALUES ('María Quispe', '987111222', 'Jr. Ancash 450, El Tambo', 34.00, '12345678', 'PENDIENTE', NULL);
INSERT INTO detalle_pedido (id_pedido, id_producto, cantidad, precio_unitario, subtotal) VALUES (1, 1, 1, 18.00, 18.00);
INSERT INTO detalle_pedido (id_pedido, id_producto, cantidad, precio_unitario, subtotal) VALUES (1, 3, 2, 8.00, 16.00);

INSERT INTO pedido (cliente_nombre, cliente_telefono, cliente_direccion, total, comprobante, estado, id_motorizado)
VALUES ('Jorge Huamán', '956333444', 'Av. Huancavelica 1020, Huancayo', 28.50, '87654321', 'ACEPTADO', 2);
INSERT INTO detalle_pedido (id_pedido, id_producto, cantidad, precio_unitario, subtotal) VALUES (2, 2, 1, 16.50, 16.50);
INSERT INTO detalle_pedido (id_pedido, id_producto, cantidad, precio_unitario, subtotal) VALUES (2, 5, 1, 12.00, 12.00);

-- Código 3: Pedro Díaz lo rechazó (para probar Reasignar)
INSERT INTO pedido (cliente_nombre, cliente_telefono, cliente_direccion, total, comprobante, estado, id_motorizado)
VALUES ('Rosa Pérez', '912555666', 'Calle Real 870, Huancayo', 20.00, '11223344', 'RECHAZADO', 3);
INSERT INTO detalle_pedido (id_pedido, id_producto, cantidad, precio_unitario, subtotal) VALUES (3, 4, 2, 6.00, 12.00);
INSERT INTO detalle_pedido (id_pedido, id_producto, cantidad, precio_unitario, subtotal) VALUES (3, 3, 1, 8.00, 8.00);

-- Código 4: asignado a José Rojas, todavía sin aceptar
INSERT INTO pedido (cliente_nombre, cliente_telefono, cliente_direccion, total, comprobante, estado, id_motorizado)
VALUES ('Luis Cárdenas', '934777888', 'Av. Mariscal Castilla 2100, El Tambo', 18.00, '55667788', 'ASIGNADO', 4);
INSERT INTO detalle_pedido (id_pedido, id_producto, cantidad, precio_unitario, subtotal) VALUES (4, 1, 1, 18.00, 18.00);

-- Código 5: entregado por Luis Gómez
INSERT INTO pedido (cliente_nombre, cliente_telefono, cliente_direccion, total, comprobante, estado, id_motorizado)
VALUES ('Ana Torres', '945999000', 'Jr. Puno 300, Huancayo', 24.00, '99887766', 'ENTREGADO', 2);
INSERT INTO detalle_pedido (id_pedido, id_producto, cantidad, precio_unitario, subtotal) VALUES (5, 5, 2, 12.00, 24.00);
