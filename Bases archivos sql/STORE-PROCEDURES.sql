#------PROCEDURES DE CROW-------
DELIMITER |
CREATE PROCEDURE mostrarCursos(IN periodo VARCHAR(10))
BEGIN
Select ID_Curso,nombreC,paralelo FROM Curso WHERE estado="ACTIVO" AND Curso.periodoLectivo=periodo;
END;|

 
CREATE PROCEDURE mostrarPensum(IN curso VARCHAR(50),IN paralelo VARCHAR(2),IN periodo VARCHAR(10))
BEGIN
Select m.ID_Materia,m.nombreM FROM Curso c,Pensum p,Materia m where c.nombreC=curso AND c.paralelo=paralelo AND c.periodoLectivo=periodo AND p.ID_Curso=c.ID_Curso AND m.ID_Materia=p.ID_Materia AND m.estado="ACTIVO";  
END;|


CREATE PROCEDURE mostrarMatricula(IN periodo VARCHAR(10)) 
BEGIN
Select NO_Matricula From Matricula where Matricula.periodo_Electivo=periodo;
END;|


CREATE PROCEDURE ingresarAlumno(
IN cedula VARCHAR(10),
IN nombre VARCHAR(40),
IN lugar VARCHAR(40),
IN fecha DATE,
IN institucion VARCHAR(40),
IN padre VARCHAR(40),
IN madre VARCHAR(40),
IN representante VARCHAR(40),
IN telRepresentante VARCHAR(40),
IN direccion VARCHAR(40),
IN genero VARCHAR(10),
IN discapacidad VARCHAR(3),
IN curso VARCHAR(40),
IN paralelo VARCHAR(5),
IN matricula VARCHAR(7),
IN usuario VARCHAR(40),
IN periodo VARCHAR(10)
)
BEGIN
DECLARE id_curso VARCHAR(15);
INSERT INTO BD_Colegio.Alumnos VALUES (cedula,nombre,lugar,fecha,institucion,padre,madre,representante,telRepresentante,direccion,genero,discapacidad);
SET id_curso=(Select Curso.ID_Curso from Curso where nombreC=curso AND Curso.paralelo=paralelo AND Curso.periodoLectivo=periodo);
INSERT INTO BD_Colegio.Matricula VALUES (matricula,id_curso,cedula,periodo,"ACTIVO");
INSERT INTO BD_Colegio.Cuenta VALUES(usuario,cedula,cedula,null);

END;|

#FALTA CREAR ESTO
CREATE PROCEDURE mostrarProfesores()
BEGIN
	SELECT e.NombreCompleto FROM Empleado e;
END;|


CREATE PROCEDURE insertarTelefonoEstudiante(IN telefono VARCHAR(13), IN cedula VARCHAR (13))
BEGIN
INSERT INTO BD_Colegio.telefonoestudiante VALUES (telefono,cedula);
END;|


CREATE PROCEDURE buscarAlumnos(IN dato varchar(50),IN tipoBusqueda int,IN periodo VARCHAR(10))
BEGIN
IF tipoBusqueda=1 THEN
	Select a.*,m.NO_Matricula,m.estado,c.nombreC,c.paralelo from Alumnos a,Matricula m,Curso c where a.cedula=m.cedula AND m.ID_Curso=c.ID_Curso AND a.nombreA=dato AND m.periodo_Electivo=periodo;
ELSE 
	IF tipoBusqueda=2 THEN
	Select a.*,m.NO_Matricula,m.estado,c.nombreC,c.paralelo from Alumnos a,Matricula m,Curso c where a.cedula=m.cedula AND m.ID_Curso=c.ID_Curso AND a.cedula=dato AND m.periodo_Electivo=periodo;
	ELSE 
		IF tipoBusqueda=3 THEN
		Select a.*,m.NO_Matricula,m.estado,c.nombreC,c.paralelo from Alumnos a,Matricula m,Curso c where a.cedula=m.cedula AND m.ID_Curso=c.ID_Curso AND m.NO_Matricula=dato AND m.periodo_Electivo=periodo;
		END IF;
	END IF;
END IF;
END;|

call buscarAlumnos("Ana Paredes","1","2016-2017")|

CREATE PROCEDURE mostrarTelefonoAlumno(IN cedula varchar(15))
BEGIN
	Select telefono from telefonoestudiante where telefonoestudiante.cedula=cedula;
END;|


CREATE PROCEDURE modificarAlumno(
IN cedula VARCHAR(10),
IN nombre VARCHAR(40),
IN lugar VARCHAR(40),
IN fecha DATE,
IN institucion VARCHAR(40),
IN padre VARCHAR(40),
IN madre VARCHAR(40),
IN representante VARCHAR(40),
IN telRepresentante VARCHAR(40),
IN direccion VARCHAR(40),
IN genero VARCHAR(10),
IN discapacidad VARCHAR(3),
IN curso VARCHAR(40),
IN paralelo VARCHAR(5),
IN usuario VARCHAR(40),
IN cedulaAnterior varchar(15),
IN periodo VARCHAR(10))
BEGIN
UPDATE BD_Colegio.Alumnos SET Alumnos.cedula=cedula,alumnos.nombreA=nombre,alumnos.lugar_Nacimiento=lugar,alumnos.fecha_Nacimiento=fecha,alumnos.institucion_Anterior=institucion,nombre_Padre=padre,nombre_Madre=madre,nombre_Representante=representante,telefono_Representante=telRepresentante,direccion=Alumnos.direccion,Alumnos.genero=genero,Alumnos.discapacidad=discapacidad where alumnos.cedula=cedulaAnterior;
DELETE FROM BD_Colegio.telefonoestudiante where telefonoestudiante.cedula=cedula;
UPDATE BD_Colegio.Matricula SET Matricula.ID_Curso=(Select Curso.ID_Curso from Curso where Curso.nombreC=curso AND Curso.paralelo=paralelo AND Curso.periodoLectivo=periodo) where Matricula.cedula=cedula AND Matricula.periodo_Electivo=periodo;
UPDATE BD_Colegio.Cuenta SET Cuenta.Usuario=usuario, clave=cedula,Cuenta.cedula=cedula where Cuenta.cedula=cedula;
END;|


CREATE PROCEDURE cambiarEstadoMatricula(IN matricula varchar(10),IN estado varchar(15))
BEGIN
UPDATE BD_Colegio.matricula SET Matricula.estado=estado where Matricula.NO_Matricula=matricula;
END;|


CREATE PROCEDURE verificarCodigoCurso()
BEGIN
	Select CAST(substring(MAX(ID_Curso),-3) AS UNSIGNED) FROM Curso;
END;|


CREATE PROCEDURE ingresarCursos(IN codigo varchar(10),IN nombre varchar(50),IN paralelo varchar(1),IN periodo varchar(10),IN estado varchar(8),IN capacidad INT)
BEGIN
	INSERT INTO Curso VALUES(codigo,nombre,paralelo,estado,periodo,capacidad,null);
END;|



CREATE PROCEDURE getCurso(IN curso VARCHAR(50),IN paralelo VARCHAR(1),periodo VARCHAR(12))
	BEGIN
    Select c.ID_Curso,c.capacidad  FROM Curso c where c.nombreC=curso AND c.paralelo=paralelo AND c.periodoLectivo=periodo;
END;|


CREATE PROCEDURE mostrarDirigente(IN curso VARCHAR(50),IN paralelo VARCHAR(1),periodo VARCHAR(12))
BEGIN
	Select e.NombreCompleto FROM Curso c NATURAL JOIN Empleado e where c.nombreC=curso AND c.paralelo=paralelo AND c.periodoLectivo=periodo;
END;|


CREATE PROCEDURE mostrarAlumnosRegistrados(IN curso VARCHAR(50),IN paralelo VARCHAR(1),periodo VARCHAR(12))
BEGIN
	Select m.NO_Matricula,a.nombreA,a.cedula,m.estado  FROM Alumnos a NATURAL JOIN Matricula m NATURAL JOIN Curso c where c.nombreC=curso AND c.paralelo=paralelo AND c.periodoLectivo=periodo ORDER BY a.nombreA;
END;|


CREATE  PROCEDURE mostrarCursosDisponibles(IN curso VARCHAR(50),IN periodo VARCHAR(12),IN cantidadEstudiantes INT)
BEGIN
	Select ID_Curso,nombreC,paralelo FROM Curso Where (Select count(m.cedula) FROM Matricula m where m.ID_Curso=Curso.ID_Curso)+cantidadEstudiantes<=Curso.capacidad AND Curso.nombreC=curso AND Curso.periodoLectivo=periodo;
END;|


CREATE PROCEDURE traspasarAlumno(IN cedula VARCHAR(10),IN codigoCurso VARCHAR(11))
BEGIN
	UPDATE Matricula SET Matricula.ID_Curso=codigoCurso where Matricula.cedula=cedula;
END;|


CREATE PROCEDURE inactivarCurso(IN idCurso varchar(11))
BEGIN
	UPDATE Curso SET Curso.estado="INACTIVO" where Curso.ID_Curso=idCurso;
END;|


#---------PROCEDURES DE JOE-----------------------------------------------------------------------------------------
DELIMITER /
CREATE PROCEDURE buscarAlumno(IN dato varchar(50), IN tipoBusqueda CHAR) BEGIN
IF tipoBusqueda=1 THEN
	Select a.*,m.NO_Matricula,m.estado,c.nombreC,c.paralelo from Alumnos a,Matricula m,Curso c where a.cedula=m.cedula AND m.ID_Curso=c.ID_Curso AND a.nombreA=dato;
ELSE 
	IF tipoBusqueda=2 THEN
	Select a.*,m.NO_Matricula,m.estado,c.nombreC,c.paralelo from Alumnos a,Matricula m,Curso c where a.cedula=m.cedula AND m.ID_Curso=c.ID_Curso AND a.cedula=dato;
	ELSE 
		IF tipoBusqueda=3 THEN
		Select a.*,m.NO_Matricula,m.estado,c.nombreC,c.paralelo from Alumnos a,Matricula m,Curso c where a.cedula=m.cedula AND m.ID_Curso=c.ID_Curso AND m.NO_Matricula=dato;
		END IF;
	END IF;
END IF;
END;/


DELIMITER /
CREATE PROCEDURE mostrarNotasPeriodo(IN dato VARCHAR(50), IN periodo VARCHAR(10), IN tipoBusqueda CHAR) BEGIN
	IF tipoBusqueda = '1' THEN
		SELECT l.ID_Materia, m.nombreM, l.nota1, l.nota2, l.promedio, l.notaSup, c.nombreC,c.paralelo FROM libreta l JOIN materia m JOIN Pensum p JOIN Curso c ON l.ID_Materia = m.ID_Materia AND p.ID_Materia=m.ID_Materia AND c.ID_Curso=p.ID_Curso  AND l.cedula = (SELECT a.cedula FROM alumnos a JOIN matricula ma ON ma.cedula = a.cedula WHERE nombreA = dato AND ma.periodo_Electivo = periodo);
	ELSE
		IF tipoBusqueda = '2' THEN
			SELECT l.ID_Materia, m.nombreM, l.nota1, l.nota2, l.promedio, l.notaSup, c.nombreC,c.paralelo FROM libreta l JOIN materia m JOIN Pensum p JOIN Curso c ON l.ID_Materia = m.ID_Materia AND p.ID_Materia=m.ID_Materia AND c.ID_Curso=p.ID_Curso AND l.cedula = (SELECT cedula FROM matricula WHERE cedula = dato AND periodo_Electivo = periodo);
		ELSE
			SELECT l.ID_Materia, m.nombreM, l.nota1, l.nota2, l.promedio, l.notaSup, c.nombreC,c.paralelo FROM libreta l JOIN materia m JOIN Pensum p JOIN Curso c ON l.ID_Materia = m.ID_Materia AND p.ID_Materia=m.ID_Materia AND c.ID_Curso=p.ID_Curso AND l.cedula = (SELECT cedula FROM matricula WHERE NO_Matricula = dato AND periodo_Electivo = periodo);
		END iF;
	END IF;
END;
/

DELIMITER /
CREATE PROCEDURE mostrarMateriaCurso(IN nomCurso VARCHAR(25), IN paralelo CHAR) BEGIN
	SELECT m.ID_Materia, m.nombreM, e.NombreCompleto FROM curso c JOIN pensum p JOIN materia m JOIN Asignacion a JOIN empleado e ON m.ID_Materia = p.ID_Materia AND c.ID_Curso = p.ID_curso AND a.ID_Materia = m.ID_Materia AND e.ID_Empleado=a.ID_Empleado AND c.nombreC = nomCurso AND c.paralelo = paralelo;
END; 
/

DELIMITER /
CREATE PROCEDURE getMaterias() BEGIN
	SELECT nombreM FROM materia;
END;
/

DELIMITER /
CREATE PROCEDURE mostrarNotasCurso(IN nomCurso VARCHAR(15), IN paralelo CHAR, IN nomMateria VARCHAR(30)) BEGIN
	SELECT ma.NO_Matricula, a.nombreA, l.nota1, l.nota2, l.promedio, l.notaSup, ma.estado FROM curso c JOIN pensum p JOIN materia m JOIN libreta l JOIN matricula ma JOIN alumnos a ON c.ID_Curso = p.ID_Curso AND m.ID_Materia = p.ID_Materia AND m.ID_Materia = l.ID_materia AND l.cedula = ma.cedula AND ma.cedula = a.cedula AND c.nombreC = nomCurso AND c.paralelo = paralelo AND m.nombreM = nomMateria;
END;
/

DELIMITER /
CREATE PROCEDURE getPeriodos() BEGIN
	SELECT DISTINCT(periodo_Electivo) FROM matricula;
END;
/
