CREATE OR REPLACE TYPE Alumno AS OBJECT(
    Codigo NUMBER(2),
    Nombre VARCHAR(50),
    Apellidos VARCHAR(100),
    Fecha_nac DATE,
    Nota NUMBER(2),
    
member procedure poner_nota(n number),
member procedure ver_nota,
member procedure ver_edad
);
/

--SOLO SE PUEDE CREAR UN MEMBER TYPE BODY CON TODOS LOS METODOS DENTRO
    
CREATE OR REPLACE TYPE BODY Alumno AS
    MEMBER PROCEDURE poner_nota(n NUMBER) IS 
    BEGIN
        --ME METO A LA PROPIEDAD NOTA DEL PROPIO OBJETO
        self.Nota:=n;
    END poner_nota;
    
    MEMBER PROCEDURE ver_nota IS
    BEGIN
    /*CONCATENO CON || y se pasa la nota para imprimirla a chars
    ELIJO MOSTRARLO DIRECTAMENTE EN LA FUNCION, NO DELVOLVERLO */
        DBMS_OUTPUT.PUT_LINE('Nota De ' || self.Nombre || ' : ' || TO_CHAR(self.Nota));
        IF Nota>=5 THEN
            DBMS_OUTPUT.PUT_LINE('Alumno ' || self.Nombre || ' esta aprobado con : ' || Nota);
        ELSE
            DBMS_OUTPUT.PUT_LINE('Alumno ' || self.Nombre || ' esta suspenso con : ' || Nota);
        END IF;
    END ver_nota;
    
    MEMBER PROCEDURE ver_edad IS
    BEGIN
        DBMS_OUTPUT.PUT_LINE('Edad De ' || self.Nombre || ' : ' || TO_CHAR(TRUNC(MONTHS_BETWEEN(SYSDATE,Fecha_nac)/12)));
        END ver_edad;
END;
/

    
--ASI SE DECLARAN E INICIALIZAN OBJETOS CON EL CONSTRUCTOR
DECLARE 
    alumno1 Alumno;
    alumno2 Alumno; 
    alumno3 Alumno;
BEGIN
    alumno1:=Alumno(1,'Luis','Ojeda',TO_DATE('22/06/1996','DD/MM/YYYY'),null);
    alumno2:=Alumno(2,'Juan','Gomez',TO_DATE('12/01/2000','DD/MM/YYYY'),null);
    alumno3:=Alumno(3,'Pepe','Gonzalez',TO_DATE('02/11/1992','DD/MM/YYYY'),null);

    alumno1.poner_nota(8);
    alumno2.poner_nota(5);
    alumno3.poner_nota(3);
    
    alumno1.ver_nota;
    alumno2.ver_nota;
    alumno3.ver_nota;
    
    alumno1.ver_edad;
    alumno2.ver_edad;
    alumno3.ver_edad;
    
END;

SET SERVEROUTPUT ON