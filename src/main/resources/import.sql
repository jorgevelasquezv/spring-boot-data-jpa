INSERT INTO clientes (nombre, apellido, email, create_at, photo) VALUES('Isaac', 'Velásquez', 'isaac@mail.com', '2022-12-27', '');
INSERT INTO clientes (nombre, apellido, email, create_at, photo) VALUES('Katherine', 'Caceres', 'katherine@mail.com', '2022-12-26', '');
INSERT INTO clientes (nombre, apellido, email, create_at, photo) VALUES('Pedro', 'Perez', 'pedrop@mail.com', '2022-12-26', '');
INSERT INTO clientes (nombre, apellido, email, create_at, photo) VALUES('Pablo', 'Pico', 'pablop@mail.com', '2022-12-26', '');
INSERT INTO clientes (nombre, apellido, email, create_at, photo) VALUES('Carlo', 'Magno', 'carlolm@mail.com', '2022-12-26', '');
INSERT INTO clientes (nombre, apellido, email, create_at, photo) VALUES('Damian', 'Diner', 'damiand@mail.com', '2022-12-26', '');
INSERT INTO clientes (nombre, apellido, email, create_at, photo) VALUES('Juan', 'Juarez', 'juanj@mail.com', '2022-12-26', '');
INSERT INTO clientes (nombre, apellido, email, create_at, photo) VALUES('Juanita', 'Jurado', 'juanitaj@mail.com', '2022-12-26', '');
INSERT INTO clientes (nombre, apellido, email, create_at, photo) VALUES('Camila', 'Camaleón', 'camilac@mail.com', '2022-12-26', '');
INSERT INTO clientes (nombre, apellido, email, create_at, photo) VALUES('Daniel', 'Duran', 'danield@mail.com', '2022-12-26', '');
INSERT INTO clientes (nombre, apellido, email, create_at, photo) VALUES('Esteban', 'Estada', 'estebane@mail.com', '2022-12-26', '');
INSERT INTO clientes (nombre, apellido, email, create_at, photo) VALUES('Liliana', 'Linares', 'lilianal@mail.com', '2022-12-26', '');
INSERT INTO clientes (nombre, apellido, email, create_at, photo) VALUES('Lorena', 'Lilo', 'lorenal@mail.com', '2022-12-26', '');
INSERT INTO clientes (nombre, apellido, email, create_at, photo) VALUES('Manuel', 'Maron', 'manuelm@mail.com', '2022-12-26', '');
INSERT INTO clientes (nombre, apellido, email, create_at, photo) VALUES('Diana', 'Diaz', 'dianad@mail.com', '2022-12-26', '');
INSERT INTO clientes (nombre, apellido, email, create_at, photo) VALUES('Bertha', 'Brito', 'berthab@mail.com', '2022-12-26', '');
INSERT INTO clientes (nombre, apellido, email, create_at, photo) VALUES('Carolina', 'Carranza', 'carolinac@mail.com', '2022-12-26', '');
INSERT INTO clientes (nombre, apellido, email, create_at, photo) VALUES('Felipe', 'Figo', 'felipef@mail.com', '2022-12-26', '');
INSERT INTO clientes (nombre, apellido, email, create_at, photo) VALUES('Gabriel', 'Guio', 'gabrielg@mail.com', '2022-12-26', '');
INSERT INTO clientes (nombre, apellido, email, create_at, photo) VALUES('Hannibal', 'Hay', 'hannibalh@mail.com', '2022-12-26', '');
INSERT INTO clientes (nombre, apellido, email, create_at, photo) VALUES('Ivan', 'Illon', 'ivani@mail.com', '2022-12-26', '');
INSERT INTO clientes (nombre, apellido, email, create_at, photo) VALUES('Jaime', 'Jazz', 'jaimej@mail.com', '2022-12-26', '');
INSERT INTO clientes (nombre, apellido, email, create_at, photo) VALUES('Jairo', 'Jota', 'jairoj@mail.com', '2022-12-26', '');
INSERT INTO clientes (nombre, apellido, email, create_at, photo) VALUES('Kevin', 'Korn', 'kevin@mail.com', '2022-12-26', '');
INSERT INTO clientes (nombre, apellido, email, create_at, photo) VALUES('Karol', 'Kill', 'karolk@mail.com', '2022-12-26', '');
INSERT INTO clientes (nombre, apellido, email, create_at, photo) VALUES('Luis', 'Lopéz', 'luisl@mail.com', '2022-12-26', '');
INSERT INTO clientes (nombre, apellido, email, create_at, photo) VALUES('Lucas', 'Lion', 'lucasl@mail.com', '2022-12-26', '');
INSERT INTO clientes (nombre, apellido, email, create_at, photo) VALUES('Merlin', 'Mago', 'merlinm@mail.com', '2022-12-26', '');
INSERT INTO clientes (nombre, apellido, email, create_at, photo) VALUES('Nicolas', 'Nulo', 'nicolasn@mail.com', '2022-12-26', '');
INSERT INTO clientes (nombre, apellido, email, create_at, photo) VALUES('Nino', 'Noreña', 'ninon@mail.com', '2022-12-26', '');


INSERT INTO productos (nombre, precio, create_at) VALUES('LG Televisor LED 40 PULG', 1100, NOW() );
INSERT INTO productos (nombre, precio, create_at) VALUES('Samsung Televisor LED 40 PULG', 1150, NOW() );
INSERT INTO productos (nombre, precio, create_at) VALUES('Olimpo Televisor LED 40 PULG', 1000, NOW() );
INSERT INTO productos (nombre, precio, create_at) VALUES('Kaley Televisor LED 40 PULG', 950, NOW() );
INSERT INTO productos (nombre, precio, create_at) VALUES('LG Televisor LED 33 PULG', 900, NOW() );
INSERT INTO productos (nombre, precio, create_at) VALUES('Samsung Televisor LED 33 PULG', 950, NOW() );
INSERT INTO productos (nombre, precio, create_at) VALUES('Olimpo Televisor LED 33 PULG', 850, NOW() );
INSERT INTO productos (nombre, precio, create_at) VALUES('Kaley Televisor LED 33 PULG', 800, NOW() );
INSERT INTO productos (nombre, precio, create_at) VALUES('LG Barra de sonido', 700, NOW() );
INSERT INTO productos (nombre, precio, create_at) VALUES('Samsung Barra de sonido', 800, NOW() );
INSERT INTO productos (nombre, precio, create_at) VALUES('Olimpo Barra de sonido', 600, NOW() );
INSERT INTO productos (nombre, precio, create_at) VALUES('Kaley Barra de sonido', 590, NOW() );
INSERT INTO productos (nombre, precio, create_at) VALUES('Teclado Gamer', 200, NOW() );
INSERT INTO productos (nombre, precio, create_at) VALUES('Mouse Gamer', 150, NOW() );


INSERT INTO facturas (descripcion, observacion, cliente_id, create_at) VALUES('Factura equipos de oficina', null, 1, NOW() );
INSERT INTO items_facturas (cantidad, factura_id, producto_id) VALUES(1, 1, 1);
INSERT INTO items_facturas (cantidad, factura_id, producto_id) VALUES(2, 1, 4);
INSERT INTO items_facturas (cantidad, factura_id, producto_id) VALUES(1, 1, 5);
INSERT INTO items_facturas (cantidad, factura_id, producto_id) VALUES(1, 1, 7);

INSERT INTO facturas (descripcion, observacion, cliente_id, create_at) VALUES('Factura Electrodomesticos', 'Alguna nota', 1, NOW() );
INSERT INTO items_facturas (cantidad, factura_id, producto_id) VALUES(3, 2, 6);
INSERT INTO items_facturas (cantidad, factura_id, producto_id) VALUES(2, 2, 9);
INSERT INTO items_facturas (cantidad, factura_id, producto_id) VALUES(1, 2, 1);
INSERT INTO items_facturas (cantidad, factura_id, producto_id) VALUES(1, 2, 10);

INSERT INTO users (username, password, enabled) VALUES('jorge', '$2a$10$s4Xn5cxYmKpuBqbqt41GqOtzirO5PHwNzWoRBfTir4bXY8AEltt9O', 1);
INSERT INTO users (username, password, enabled) VALUES('admin', '$2a$10$AUiBSRwVvUG53OwUaBZfT.cS2qiEtkMHLj633eac.LtDEcohgRuS6', 1);
INSERT INTO authorities (user_id, authority) VALUES(1, 'ROLE_USER');
INSERT INTO authorities (user_id, authority) VALUES(2, 'ROLE_USER');
INSERT INTO authorities (user_id, authority) VALUES(2, 'ROLE_ADMIN');

