--TIPO OBJETO
/*  TYPE OBJECT:

TIP_DIRECCION (calle, población, codpostal, provincia)
TIP_CLIENTE (idcliente, nombre, nif, objeto tip_direccion, varray tip_telefonos)
TIP_PRODUCTO (idproducto, descripcion, PVP, stockactual)
TIP_LINEAVENTA (numerolinea, idproducto REF a objeto TIP_PRODUCTO, cantidad)
TIP_VENTA (idventa, idcliente REF a objeto TIP_CLIENTE, fechaventa, LINEAS tabla anidada del tipo 
TIP_LINEAS_VENTA, función miembro TOTAL_VENTA que retorna el precio total de la venta) */




--TYPE ARRAY
/* TIP_TELEFONOS (hasta 3 telefonos VARCHAR(15) */





--TYPE TABLE
/* TIP_LINEAS_VENTA (tabla anidada de objetos de tipo TIP_LINEAVENTA */

-- 1. Definir un tipo varray de dimensión 3 para contener los teléfonos
CREATE OR REPLACE TYPE tipo_telefonos AS VARRAY(3) OF VARCHAR2(15);
/
-- 2. Crear los tipos dirección, cliente, producto y línea de venta
CREATE OR REPLACE TYPE tipo_direccion AS OBJECT (
    calle VARCHAR2(50),
    poblacion VARCHAR2(50),
    codpostal NUMBER(5),
    provincia VARCHAR2(40)
);
/

CREATE OR REPLACE TYPE tipo_cliente AS OBJECT(
    idcliente NUMBER,
    nombre VARCHAR2(50),
    nif VARCHAR2(9),
    direccion tipo_direccion,
    telefonos tipo_telefonos
);

CREATE OR REPLACE TYPE tipo_producto AS OBJECT(
    idproducto NUMBER,
    descripcion VARCHAR2(80),
    pvp NUMBER,
    stockactual NUMBER
);
/
CREATE OR REPLACE TYPE tipo_lineaventa AS OBJECT(
    numerolinea NUMBER,
    producto REF tipo_producto,
    cantidad NUMBER
);
/
-- 3. Crear un tipo tabla anidada para contener las líneas de una venta
CREATE OR REPLACE TYPE tipo_lineas_venta AS TABLE OF tipo_lineaventa;
/
-- 4. Crear un tipo venta para los datos de las ventas, cada venta tendrá un atributo LINEAS del tipo tabla
-- anidada definida anteriormente
CREATE OR REPLACE TYPE tipo_venta AS OBJECT(
    idventa NUMBER,
    idcliente REF tipo_cliente,
    fechaventa DATE,
    lineas tipo_lineas_venta,
    MEMBER FUNCTION total_venta RETURN number
);
/
-- 5. Crea el cuerpo del tipo anterior, teniendo en cuenta que se definirá la función miembro TOTAL_VENTA que
-- calcula el total de la venta de las líneas de venta que forman parte de una venta
CREATE OR REPLACE TYPE BODY tipo_venta AS
    MEMBER FUNCTION total_venta RETURN NUMBER IS
        total NUMBER := 0;
        precio_producto NUMBER;
    BEGIN
        IF lineas IS NOT NULL THEN
            FOR i IN 1..lineas.COUNT LOOP
                -- CORRECCIÓN: Esta es la forma correcta de usar el REF
                SELECT pvp INTO precio_producto
                FROM tabla_productos p
                WHERE REF(p) = lineas(i).producto;
                
                total := total + (precio_producto * lineas(i).cantidad);
            END LOOP;
        END IF;
        RETURN total;
    END total_venta;
END;
/
-- 6. Crear las tablas donde almacenar los objetos de la aplicación. Se creará una tabla para clientes, otra para
-- productos y otra para las ventas, en dichas tablas se definirán las oportunas claves primarias.
CREATE TABLE tabla_clientes OF tipo_cliente(
    PRIMARY KEY (idcliente),
    UNIQUE (nif)
);

CREATE TABLE tabla_productos OF tipo_producto (
    PRIMARY KEY (idproducto)
);

CREATE TABLE tabla_ventas OF tipo_venta (
    PRIMARY KEY (idventa)
) NESTED TABLE lineas STORE AS nt_lineas_venta;

-- 7. Inserta dos clientes y cinco productos.
INSERT INTO tabla_clientes VALUES (
    tipo_cliente(
        1,
        'Juan Pérez',
        '12345678A',
        tipo_direccion('Calle 1','Cuenca',28001,'Cuenca'),
        tipo_telefonos('324565435','123456789','111222333')
    )
);

INSERT INTO tabla_clientes VALUES (
    tipo_cliente(
        2,
        'Ana López',
        '11122234B',
        tipo_direccion('Avenida 10','Cuenca',41001,'Cuenca'),
        tipo_telefonos('222333444',NULL,NULL)
    )
);

INSERT INTO tabla_productos VALUES (tipo_producto(1,'Teclado',20,100));
INSERT INTO tabla_productos VALUES (tipo_producto(2,'Raton',10,200));
INSERT INTO tabla_productos VALUES (tipo_producto(3,'Monitor',150,50));
INSERT INTO tabla_productos VALUES (tipo_producto(4,'Impresora',120,30));
INSERT INTO tabla_productos VALUES (tipo_producto(5,'USB',15,300));

COMMIT;

-- 8. Insertar en TABLA_VENTAS la venta con IDVENTA 1 para el IDCLIENTE 1
INSERT INTO tabla_ventas VALUES (
    tipo_venta(
        1,
        (SELECT REF(tabla_clientes) FROM tabla_clientes WHERE idcliente = 1),
        SYSDATE,
        tipo_lineas_venta()
    )
);

-- 9. Insertar en TABLA_VENTAS dos líneas de venta para el IDVENTA 1 para los productos 1 (la CANTIDAD es 1) y 2 (la CANTIDAD es 2)
INSERT INTO TABLE (
    SELECT lineas FROM tabla_ventas WHERE idventa = 1
) VALUES (
    tipo_lineaventa(
        1,
        (SELECT REF(tabla_productos) FROM tabla_productos WHERE idproducto = 1),
        1
    )
);

INSERT INTO TABLE (
    SELECT lineas FROM tabla_ventas WHERE idventa = 1
) VALUES (
    tipo_lineaventa(
        2,
        (SELECT REF(tabla_productos) FROM tabla_productos WHERE idproducto = 2),
        2
    )
);

-- 10. Insertar en TABLA_VENTAS la venta con IDVENTA 2 para el IDCLIENTE
INSERT INTO tabla_ventas VALUES (
    tipo_venta(
        2,
        (SELECT REF(tabla_clientes) FROM tabla_clientes WHERE idcliente = 2),
        SYSDATE,
        tipo_lineas_venta()
    )
);

-- 11. Insertar en TABLA_VENTAS tres líneas de venta para el IDVENTA 2 para los productos 1 
-- (la CANTIDAD es 2), 4 (la CANTIDAD es 1) y 5 (la CANTIDAD es 4)
INSERT INTO TABLE (
    SELECT lineas FROM tabla_ventas WHERE idventa = 2
) VALUES (
    tipo_lineaventa(
        1,
        (SELECT REF(tabla_productos) FROM tabla_productos WHERE idproducto = 1),
        2
    )
);

INSERT INTO TABLE (
    SELECT lineas FROM tabla_ventas WHERE idventa = 2
) VALUES (
    tipo_lineaventa(
        2,
        (SELECT REF(tabla_productos) FROM tabla_productos WHERE idproducto = 4),
        1
    )
);

INSERT INTO TABLE (
    SELECT lineas FROM tabla_ventas WHERE idventa = 2
) VALUES (
    tipo_lineaventa(
        3,
        (SELECT REF(tabla_productos) FROM tabla_productos WHERE idproducto = 5),
        4
    )
);

COMMIT;

-- 12. Visualizar la función TOTAL_VENTA del tipo TIP_VENTA del que se ha generado la tabla TABLA_VENTAS.
SELECT 
    idventa,
    total_venta() AS total
FROM tabla_ventas;