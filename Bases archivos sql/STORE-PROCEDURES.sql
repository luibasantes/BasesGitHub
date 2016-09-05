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


CREATE PROCEDURE mostrarMaterias(IN age VARCHAR(10))
BEGIN
Select Materia.nombreM  FROM Materia where Materia.ID_Materia LIKE concat(age,"%") AND Materia.estado="ACTIVO"; 
END;|


CREATE PROCEDURE mostrarCursosAsignados(IN idMateria VARCHAR(10),IN age VARCHAR(4))
BEGIN
	Select c.ID_Curso,c.nombreC,c.paralelo,c.periodoLectivo,c.estado  FROM Curso c JOIN Pensum p JOIN Materia m ON  c.ID_Curso=p.ID_Curso and p.ID_Materia=m.ID_Materia where m.ID_Materia=idMateria and m.ID_Materia LIKE concat(age,"%");
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


CREATE PROCEDURE obtenerMateriasAsignaciones(IN curso VARCHAR(50), IN paralelo VARCHAR(1),IN periodo VARCHAR(10))
BEGIN
	DECLARE idCurso VARCHAR(10);
	SET idCurso=(Select idCurso FROM Curso WHERE Curso.nombreC=curso AND Curso.paralelo=paralelo AND Curso.periodoLectivo=periodo);
	Select Asignacion.ID_Materia FROM Asignacion WHERE Asignacion.ID_Curso=idCurso AND asignacion.periodoLetivo=periodo;
END;|

CREATE PROCEDURE crearLibreta(IN cedula VARCHAR(10), IN idMateria VARCHAR(10),periodo VARCHAR(10))
BEGIN
	DECLARE idCurso Varchar(10);
    DECLARE idEmpleado Varchar(10);
    SET idCurso=(Select c.ID_Curso FROM Curso c JOIN Matricula m ON c.ID_Curso=m.ID_Curso WHERE m.cedula=cedula AND m.periodo_Electivo=periodo);
    IF idCurso IN (Select Asignacion.ID_Curso FROM Asignacion WHERE Asignacion.periodoLetivo=periodo) THEN
		SET idEmpleado=(Select Asignacion.ID_Empleado FROM Asignacion WHERE Asignacion.ID_Curso=idCurso AND Asignacion.ID_Materia=idMateria AND Asignacion.periodoLetivo=periodo);
		INSERT INTO Libreta VALUES(cedula,idMateria,idEmpleado,0,0,0,0);
	END IF;
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

DELIMITER |
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
DELETE FROM BD_Colegio.libreta WHERE libreta.cedula=cedula;
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



CREATE PROCEDURE verificarCodigoMateria(IN codigoMateria varchar(10),IN age varchar(4))
BEGIN
	IF codigoMateria IN (Select Materia.ID_Materia FROM Materia where Materia.ID_Materia LIKE concat(age,"%")) THEN
		Select 1;
	ELSE
		Select 0;
	END IF;
END;|


CREATE PROCEDURE asignarMaterias(IN codigoMateria VARCHAR(50), IN nombreMateria VARCHAR(50),IN nombreCurso VARCHAR(50), IN paralelo VARCHAR(1), IN periodo VARCHAR(10))
BEGIN
	IF codigoMateria NOT IN (Select ID_Materia FROM Materia where Materia.ID_Materia LIKE concat(substring(periodo,1,4),"%")) THEN
		INSERT Materia Values(codigoMateria,nombreMateria,"ACTIVO");
	END IF;
    INSERT INTO Pensum VALUES((Select Curso.ID_Curso FROM Curso WHERE Curso.nombreC=nombreCurso AND Curso.paralelo=paralelo AND Curso.periodoLectivo=periodo),codigoMateria,periodo);
END;|


CREATE PROCEDURE getMateria(IN nombre VARCHAR(50),IN age VARCHAR(4))
BEGIN
	Select Materia.ID_Materia FROM Materia Where Materia.nombreM=nombre AND Materia.ID_Materia LIKE concat(age,"%");
END;|


CREATE PROCEDURE inactivarMateria(IN idMateria VARCHAR(50))
BEGIN
	UPDATE Materia SET Materia.estado="INACTIVO" where Materia.ID_Materia=idMateria;
    DELETE FROM Asignacion where Asignacion.ID_Materia=idMateria;
    DELETE FROM Libreta WHERE Libreta.ID_Materia=idMateria;
END;|
#libreta.ID_Empleado=(Select a.ID_Empleado FROM Libreta l JOIN Materia m JOIN Asignacion a ON l.ID_Materia=m.ID_Materia AND a.ID_Materia=m.ID_Materia WHERE a.ID_Materia=idMateria AND a.ID_Curso=(Select Curso.ID_Curso FROM Curso where Curso.nombreC=curso AND Curso.paralelo=paralelo AND Curso.periodoLectivo=periodo) )

CREATE PROCEDURE modificarNotas(
IN matricula VARCHAR(10),
IN idMateria VARCHAR(10),
IN curso VARCHAR(50),
IN paralelo VARCHAR(1), 
IN nota1 FLOAT,
IN nota2 FLOAT,
IN sup FLOAT,
In prom FLOAT,
In periodo VARCHAR(10)
)
BEGIN
	DECLARE idEmp VARCHAR(15);
    SET idEmp= (Select distinct a.ID_Empleado FROM Libreta l JOIN Materia m JOIN Asignacion a ON l.ID_Materia=m.ID_Materia AND a.ID_Materia=m.ID_Materia WHERE a.ID_Materia=idMateria AND a.ID_Curso=(Select Curso.ID_Curso FROM Curso where Curso.nombreC=curso AND Curso.paralelo=paralelo AND Curso.periodoLectivo=periodo) );
    UPDATE BD_Colegio.libreta SET libreta.nota1=nota1,libreta.nota2=nota2,libreta.notaSup=sup, libreta.promedio=prom
    WHERE libreta.cedula=(Select Matricula.cedula FROM Matricula WHERE Matricula.NO_Matricula=matricula) AND Libreta.ID_Materia=idMateria AND libreta.ID_Empleado=idEmp;
END;|


CREATE PROCEDURE mostrarTelefonoEmpleado(IN id varchar(15))
BEGIN
	Select telefono from telefonoempleado WHERE telefonoempleado.ID_Empleado=id;
END;|


CREATE PROCEDURE verificarID(IN id varchar(10),IN cedula VARCHAR(10))
BEGIN
	IF id NOT IN (Select Empleado.ID_Empleado FROM Empleado) THEN
		IF cedula NOT IN (Select Empleado.CIPasaporte FROM Empleado) THEN
			Select 0;
		END IF;
    ELSE
		Select 1;
	END IF;
END;|


CREATE PROCEDURE ingresarEmpleado(
IN id VARCHAR(10),
IN cedula VARCHAR(10),
IN nombre VARCHAR(50),
IN lugar VARCHAR(50),
IN fecha DATE,
IN genero VARCHAR(12),
IN direccion VARCHAR(50),
IN discapacidad VARCHAR(2),
IN estado VARCHAR(12),
IN nivel VARCHAR(30),
IN titulo VARCHAR(50),
IN age VARCHAR(2),
IN jornada VARCHAR(12),
IN usuario VARCHAR(50))
BEGIN
	IF nombre NOT IN (Select NombreCompleto FROM Empleado) THEN
		INSERT INTO Empleado VALUES(id,cedula,nombre,genero,lugar,fecha,direccion,discapacidad,estado,nivel,titulo,age,jornada);
		INSERT INTO Cuenta VALUES(usuario,cedula,null,id);
	ELSE
		INSERT INTO Empleado VALUES(id,cedula,nombre,genero,lugar,fecha,direccion,discapacidad,estado,nivel,titulo,age,jornada);
		INSERT INTO Cuenta VALUES(substring(usuario,"1"),cedula,null,id);
    END IF;
END;|


CREATE PROCEDURE ingresarTelefonoEmpleado(IN id VARCHAR(10),IN telefono VARCHAR(12))
	BEGIN
    INSERT INTO telefonoempleado VALUES (telefono,id);
	END;|


CREATE PROCEDURE busquedaEmpleado(IN dato varchar(50),IN tipoBusqueda int)
BEGIN
IF tipoBusqueda=1 THEN
	Select * from Empleado where Empleado.NombreCompleto=dato;
ELSE 
	IF tipoBusqueda=2 THEN
	Select * from Empleado where Empleado.ID_Empleado=dato;
    ELSE 
		IF tipoBusqueda=3 THEN
		Select * from Empleado where Empleado.CIPasaporte=dato;
        END IF;
	END IF;
END IF;
END;|

CREATE PROCEDURE modificarEmpleado(
IN cedula VARCHAR(10),
IN nombre VARCHAR(50),
IN lugar VARCHAR(50),
IN fecha DATE,
IN genero VARCHAR(12),
IN direccion VARCHAR(50),
IN discapacidad VARCHAR(2),
IN estado VARCHAR(12),
IN nivel VARCHAR(30),
IN titulo VARCHAR(50),
IN age VARCHAR(2),
IN jornada VARCHAR(12),
IN usuario VARCHAR(50),
IN id	VARCHAR(10))
BEGIN
DELETE FROM telefonoempleado WHERE telefonoempleado.ID_Empleado=id;
UPDATE Empleado SET Empleado.CIPasaporte=cedula,Empleado.NombreCompleto=nombre,Empleado.lugar_Nacimiento=lugar,Empleado.fecha_Nacimiento=fecha,
Empleado.genero=genero,Empleado.direccion=direccion,Empleado.discapacidad=discapacidad,Empleado.estado_Civil=estado,Empleado.nivel_EStudios=nivel,
Empleado.titulo=titulo,Empleado.años_servicio=age,Empleado.jornada=jornada WHERE Empleado.ID_Empleado=id;
UPDATE Cuenta SET Cuenta.Usuario=usuario,Cuenta.clave=cedula WHERE Cuenta.ID_Empleado=id;
END;|


CREATE PROCEDURE verificarNombramiento(IN nombramiento VARCHAR(15))
BEGIN
	IF nombramiento IN (Select Contrato.codigoNombramiento FROM Contrato) THEN
		select 1;
	ELSE
		select 0;
    END IF;
END;|

CREATE PROCEDURE asignarNombramiento(
IN id VARCHAR(10),
IN nombreDepartamento VARCHAR(50),
IN nombreCargo VARCHAR(50),
IN fechaInicio VARCHAR(30),
IN fechaFin VARCHAR(30),
IN codigoNombramiento VARCHAR(15),
IN tipoNombramiento VARCHAR(11),
IN categoria VARCHAR(50),
IN sueldo FLOAT,
IN tipoCargo INT
)
BEGIN
DECLARE codigoCargo VARCHAR(10);
DECLARE codigoDepartamento VARCHAR(7);
IF tipoCargo=1 THEN
	IF nombreCargo NOT IN (Select Cargo.descripcion FROM Cargo) THEN	
		INSERT INTO Cargo VALUES(concat("PROF","0",(select * FROM ultimocargo)+1),nombreCargo);
	END IF;
    SET codigoCargo=(Select Cargo.ID_Cargo FROM Cargo WHERE Cargo.descripcion=nombreCargo);
    SET codigoDepartamento=(Select Departamento.ID_Departamento FROM Departamento WHERE Departamento.nombreD=nombreDepartamento);
    INSERT INTO Contrato VALUES(id,codigoDepartamento,codigoCargo,str_to_date(fechainicio,'%Y/%m/%d'),str_to_date(fechaFin,'%Y/%m/%d'),codigoNombramiento,tipoNombramiento,categoria,sueldo);
END IF;
IF tipoCargo=2 THEN
	IF nombreCargo NOT IN (Select Cargo.descripcion FROM Cargo) THEN	
        INSERT INTO Cargo VALUES(concat("ADMN","0",(select * FROM ultimocargo)+1),nombreCargo);
	END IF;
    SET codigoCargo=(Select Cargo.ID_Cargo FROM Cargo WHERE Cargo.descripcion=nombreCargo);
    SET codigoDepartamento=(Select Departamento.ID_Departamento FROM Departamento WHERE Departamento.nombreD=nombreDepartamento);
    INSERT INTO Contrato VALUES(id,codigoDepartamento,codigoCargo,str_to_date(fechainicio,'%Y/%m/%d'),str_to_date(fechaFin,'%Y/%m/%d'),codigoNombramiento,tipoNombramiento,categoria,sueldo);
END IF;
END;|

CREATE PROCEDURE insertarAsignacion(IN id VARCHAR(10),IN curso VARCHAR(50),IN paralelo VARCHAR(1),IN materia VARCHAR(50),IN periodo VARCHAR(10))
BEGIN
	DECLARE codigoMateria VARCHAR(10);
    DECLARE codigoCurso VARCHAR(10);
    SET codigoMateria=(Select Materia.ID_Materia FROM Materia WHERE Materia.nombreM=materia AND Materia.ID_Materia LIKE concat(substr(periodo,1,4),"%"));
	SET codigoCurso=(Select Curso.ID_Curso FROM Curso WHERE Curso.nombreC=curso AND Curso.paralelo=paralelo AND Curso.periodoLectivo=periodo);
    INSERT INTO Asignacion VALUES(codigoMateria,codigoCurso,id,periodo);
END;|


CREATE PROCEDURE mostrarProfesoresDisponibles()
BEGIN
	Select e.NombreCompleto FROM Empleado e WHERE e.ID_Empleado IN (Select  Contrato.Empleado FROM Contrato  WHERE Contrato.Departamento="DEP001" AND curdate()>=Contrato.fechaI AND curdate()<Contrato.fechaF);
END|

CREATE PROCEDURE esDirigente(IN dirigente VARCHAR(50))
BEGIN
	IF dirigente IN (Select e.NombreCompleto FROM Empleado e JOIN Curso c ON e.ID_Empleado=c.ID_Empleado) THEN
		Select 1;
	ELSE
		Select 0;
	END IF;
END;|


CREATE PROCEDURE asignarDirigente(IN dirigente VARCHAR(50),IN curso VARCHAR(50),IN paralelo VARCHAR(1),IN periodo VARCHAR(12))
BEGIN
	UPDATE Curso SET Curso.ID_Empleado=(Select Empleado.ID_Empleado FROM Empleado Where Empleado.NombreCompleto=dirigente) WHERE Curso.nombreC=curso AND Curso.paralelo=paralelo AND Curso.periodoLectivo=periodo;
END;|


CREATE PROCEDURE proof(IN dato DATE)
BEGIN
IF dato > "2016-09-10" THEN
	select 1;
ELSE
	select 0;
END IF;
END|

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
		SELECT l.ID_Materia, m.NombreM, l.nota1, l.nota2, l.promedio, l.notaSup, c.nombreC, c.paralelo FROM matricula ma JOIN libreta l JOIN materia m JOIN Pensum p JOIN Curso c ON l.ID_Materia = m.ID_Materia AND p.ID_Materia=m.ID_Materia AND c.ID_Curso=p.ID_Curso AND ma.ID_Curso = c.ID_Curso AND ma.cedula = l.cedula AND l.cedula = (SELECT a.cedula FROM alumnos a JOIN matricula ma ON ma.cedula = a.cedula WHERE nombreA = dato AND ma.periodo_Electivo = periodo);
	ELSE
		IF tipoBusqueda = '2' THEN
			SELECT l.ID_Materia, m.NombreM, l.nota1, l.nota2, l.promedio, l.notaSup, c.nombreC, c.paralelo FROM matricula ma JOIN libreta l JOIN materia m JOIN Pensum p JOIN Curso c ON l.ID_Materia = m.ID_Materia AND p.ID_Materia=m.ID_Materia AND c.ID_Curso=p.ID_Curso AND ma.ID_Curso = c.ID_Curso AND ma.cedula = l.cedula AND l.cedula = (SELECT cedula FROM matricula WHERE cedula = dato AND periodo_Electivo = periodo);
		ELSE
			SELECT l.ID_Materia, m.NombreM, l.nota1, l.nota2, l.promedio, l.notaSup, c.nombreC, c.paralelo FROM matricula ma JOIN libreta l JOIN materia m JOIN Pensum p JOIN Curso c ON l.ID_Materia = m.ID_Materia AND p.ID_Materia=m.ID_Materia AND c.ID_Curso=p.ID_Curso AND ma.ID_Curso = c.ID_Curso AND ma.cedula = l.cedula AND l.cedula = (SELECT cedula FROM matricula WHERE NO_Matricula = dato AND periodo_Electivo = periodo);
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
CREATE PROCEDURE mostrarNotasCurso(IN nomCurso VARCHAR(50), IN paralelo CHAR, IN nomMateria VARCHAR(30),IN periodo VARCHAR(10)) BEGIN
	SELECT m.NO_Matricula, a.nombreA, l.nota1, l.nota2, l.notaSup FROM alumnos a, libreta l, matricula m, materia ma WHERE a.cedula = m.cedula AND a.cedula = l.cedula AND ma.ID_Materia = l.ID_Materia AND ma.nombreM = nomMateria AND m.ID_Curso = (SELECT ID_Curso FROM curso c WHERE c.nombreC = nomCurso AND c.paralelo = paralelo AND c.periodoLectivo = periodo);
END;
/

DELIMITER /
CREATE PROCEDURE getPeriodos() BEGIN
	SELECT DISTINCT(periodo_Electivo) FROM matricula;
END;
/

DELIMITER /
CREATE PROCEDURE getCursos(IN periodo VARCHAR(10)) BEGIN
	SELECT nombreC FROM curso where Curso.estado = "ACTIVO" AND periodoLectivo = periodo;
END;
/

DELIMITER /
CREATE PROCEDURE getParalelos(IN nomCurso VARCHAR(25), IN periodo VARCHAR(10)) BEGIN
	SELECT paralelo FROM Curso WHERE nombreC = nomCurso AND periodoLectivo = periodo AND estado = "ACTIVO";
END;
/

DELIMITER /
CREATE PROCEDURE getMaterias(IN nomCurso VARCHAR(25), IN periodo VARCHAR(10)) BEGIN
	SELECT distinct m.nombreM,m.ID_Materia FROM Curso c JOIN Materia m JOIN pensum p ON c.ID_Curso = p.ID_Curso AND m.ID_Materia = p.ID_Materia AND c.nombreC = nomCurso AND c.periodoLectivo = periodo;
END;
/

DELIMITER /
CREATE PROCEDURE getNombre(IN ID VARCHAR(15)) BEGIN
	SELECT nombreA FROM alumnos WHERE cedula = ID;
END;
/
DELIMITER /
CREATE PROCEDURE getRector() BEGIN
Select Empleado.NombreCompleto from Contrato,Cargo,Empleado where Contrato.Empleado=Empleado.ID_Empleado and Cargo.ID_Cargo=Contrato.Cargo and Cargo.descripcion="Rector";
END;
/
DELIMITER /
CREATE PROCEDURE getViceRector() BEGIN
Select Empleado.NombreCompleto from Contrato,Cargo,Empleado where Contrato.Empleado=Empleado.ID_Empleado and Cargo.ID_Cargo=Contrato.Cargo and Cargo.descripcion="Vicerrector"; 
END;
/
DELIMITER /
CREATE PROCEDURE getContarAlumnos(IN x VARCHAR(10)) BEGIN
Select count(NO_Matricula) from Matricula where periodo_Electivo=x;
END;
/
DELIMITER /
CREATE PROCEDURE getContarProfesores() BEGIN
Select count(Empleado.ID_Empleado) from Contrato,Cargo,Empleado where Contrato.Empleado=Empleado.ID_Empleado and Cargo.ID_Cargo=Contrato.Cargo and Cargo.ID_Cargo LIKE "PROF%";
END;
/
DELIMITER /
CREATE PROCEDURE getInfoEmpleado(IN id Varchar(20)) BEGIN
Select e.ID_Empleado,e.NombreCompleto,e.fecha_Nacimiento,e.genero,e.direccion,e.discapacidad,e.nivel_EStudios,e.titulo,Car.descripcion,e.estado_Civil,sum(c.sueldo),d.Descripcion,e.jornada
 From Empleado e,Contrato c,Departamento d, Cargo car where e.ID_Empleado = c.Empleado and c.Departamento=d.ID_Departamento and Car.ID_Cargo = c.Cargo and e.ID_Empleado=id GROUP BY c.Empleado;
END;
/


DELIMITER /
CREATE procedure getDepartamentos() BEGIN
select nombreD,descripcion from Departamento ;
END;
/

DELIMITER /
CREATE procedure getInfoDepartamentos(In descripcionD VARCHAR(50)) BEGIN
SELECT empleado.ID_Empleado,empleado.NombreCompleto,cargo.descripcion FROM empleado,cargo,departamento,contrato where empleado.ID_Empleado=contrato.Empleado and cargo.ID_Cargo=contrato.Cargo and departamento.ID_Departamento=contrato.Departamento and Departamento.nombreD=descripcionD;
END;
/

DELIMITER /
CREATE PROCEDURE buscarEmpleado(IN dato VARCHAR(30), IN tipoBusqueda CHAR) BEGIN
	IF tipoBusqueda=1 THEN
		SELECT e.*, c.descripcion, d.descripcion AS dep, SUM(con.sueldo) FROM empleado e JOIN cargo c JOIN contrato con JOIN Departamento d ON e.ID_Empleado = con.Empleado AND con.Cargo = c.ID_Cargo AND con.Departamento = d.ID_Departamento AND e.ID_Empleado = dato GROUP BY ID_Empleado;
	ELSE 
		IF tipoBusqueda=2 THEN
		SELECT e.*, c.descripcion, d.descripcion AS dep, SUM(con.sueldo) FROM empleado e JOIN cargo c JOIN contrato con JOIN Departamento d ON e.ID_Empleado = con.Empleado AND con.Cargo = c.ID_Cargo AND con.Departamento = d.ID_Departamento AND e.CIPasaporte = dato GROUP BY ID_Empleado;
		ELSE 
			IF tipoBusqueda=3 THEN
			SELECT e.*, c.descripcion, d.descripcion AS dep, SUM(con.sueldo) FROM empleado e JOIN cargo c JOIN contrato con JOIN Departamento d ON e.ID_Empleado = con.Empleado AND con.Cargo = c.ID_Cargo AND con.Departamento = d.ID_Departamento AND e.NombreCompleto = dato GROUP BY ID_Empleado;
			END IF;
		END IF;
	END IF;
END;
/

DELIMITER /
CREATE PROCEDURE getNombreDepartamento(IN id VARCHAR(15)) BEGIN
	SELECT d.descripcion FROM departamento d WHERE d.ID_Departamento = (SELECT Departamento FROM contrato WHERE empleado = id);
END;
/

DELIMITER /
CREATE PROCEDURE getInfoAlumnos(In id Varchar(15)) BEGIN
	SELECT m.NO_Matricula,a.cedula,a.nombreA,a.fecha_Nacimiento,a.direccion,telf.telefono,a.discapacidad,a.genero,a.nombre_Padre,a.nombre_Madre,a.nombre_Representante,a.telefono_Representante,c.nombreC,m.estado,a.institucion_Anterior
    from alumnos a, matricula m,telefonoestudiante telf,curso c where a.cedula=id and m.cedula=a.cedula and telf.cedula=a.cedula and m.ID_Curso= c.ID_Curso;
END;
/

DELIMITER /
CREATE PROCEDURE getCuentas() BEGIN
	Select * from Cuenta;
END;
/

DELIMITER /
CREATE PROCEDURE getCargo(In id Varchar(15)) BEGIN
		SELECT con.Cargo from Empleado e,Contrato con where con.Empleado=e.ID_Empleado and con.Empleado=id;
END;
/

DELIMITER /
CREATE PROCEDURE verificarDepartamento(IN id VARCHAR(6)) BEGIN
	IF id IN (SELECT ID_Departamento FROM Departamento) THEN
		SELECT 0;
	ELSE
		SELECT 1;
	END IF;
END;
/

DELIMITER /
CREATE PROCEDURE guardarDepartamento(IN id VARCHAR(6), IN nombre VARCHAR(20), IN descripcion VARCHAR(40)) BEGIN
	INSERT INTO departamento VALUES(id, nombre, descripcion);
END;
/

DELIMITER /
CREATE procedure getDepartamentosID() BEGIN
select ID_Departamento,nombreD from Departamento ;
END;
/

DELIMITER /
CREATE procedure getDepartamentosInfo(In id varchar(15)) BEGIN
select nombreD,descripcion from departamento where departamento.ID_Departamento=id;
END;
/

DELIMITER /
CREATE procedure modificarDepartamento(In id varchar(15),IN nom varchar(50),IN descrip varchar(50)) BEGIN
Update departamento set departamento.nombreD = nom where departamento.ID_Departamento=id;
Update departamento set departamento.descripcion = descrip where departamento.ID_Departamento=id;
END;
/

DELIMITER /
CREATE procedure eliminarDepartamento(In id varchar(15)) BEGIN
DELETE FROM departamento where departamento.ID_Departamento=id;
END;
/