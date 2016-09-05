
create database BD_Colegio; 
use BD_Colegio;

create table Empleado(
    ID_Empleado varchar(10) primary key,
    CIPasaporte varchar(10),
    NombreCompleto varchar(60),
    genero varchar(20),
    lugar_Nacimiento varchar(40),
    fecha_Nacimiento date,
    direccion varchar(50),
    discapacidad varchar(30),
    estado_Civil varchar(25),
    nivel_EStudios varchar(40),
    titulo varchar(70),
    años_servicio int,
    jornada varchar(30)
);

create table Cargo(
    ID_Cargo varchar(10) primary key not null,
    descripcion varchar(60)
);

create table Departamento(
    ID_Departamento varchar(10) PRIMARY KEY,
    nombreD varchar(40),
    descripcion varchar(80)
);

create table Contrato(
    Empleado varchar(10) ,
    Departamento varchar(10) ,
    Cargo varchar(10),
    fechaI date,
    fechaF date,
    codigoNombramiento varchar(10) PRIMARY KEY,
    tipoNombramiento varchar(30),
    categoria varchar(60),
    sueldo double,
    FOREIGN KEY (Empleado) REFERENCES Empleado (ID_Empleado) ON  UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY (Departamento) REFERENCES Departamento (ID_Departamento),
    FOREIGN KEY (Cargo) REFERENCES Cargo (ID_Cargo)
);

create table Alumnos(
    cedula varchar(10) primary key not null,
    nombreA varchar(30),
    lugar_Nacimiento varchar(40),
    fecha_Nacimiento date,
    institucion_Anterior varchar(30),
    nombre_Padre varchar(30),
    nombre_Madre varchar(30),
    nombre_Representante varchar(30),
    telefono_Representante varchar(12),
    direccion varchar(80),
    genero varchar(25),
    discapacidad varchar(15)
);

create table Cuenta(
    Usuario varchar(40) primary key not null,
    clave varchar(16) not null,
    cedula varchar(10),
    ID_Empleado varchar(10),
    FOREIGN KEY (cedula) REFERENCES Alumnos (cedula) on delete cascade on update cascade,
    FOREIGN KEY (ID_Empleado) REFERENCES Empleado (ID_Empleado) on delete cascade on update cascade
);

create table Materia (
    ID_Materia varchar(10) primary key,
    nombreM varchar(50) NOT NULL,
    estado varchar(25) NOT NULL
);

create table Libreta(
    cedula varchar(10) NOT NULL,
    ID_Materia varchar(10) NOT NULL, 
    ID_Empleado varchar(10) NOT NULL,
    nota1 float,
    nota2 float,
    notaSup float,
    promedio float,
    FOREIGN KEY (cedula) REFERENCES Alumnos (cedula) on delete cascade on update cascade,
    FOREIGN KEY (ID_Materia) REFERENCES Materia (ID_Materia),
    FOREIGN KEY (ID_Empleado ) REFERENCES Empleado (ID_Empleado ) on delete cascade on update cascade
);


create table Curso(
    ID_Curso varchar(10) primary key not null,
    nombreC varchar(30) NOT NULL,
    paralelo varchar(25) NOT NULL,
    estado varchar(35) NOT NULL,
    periodoLectivo varchar(25) NOT NULL,
    capacidad int NOT NULL,
    ID_Empleado varchar(10),
    FOREIGN KEY (ID_Empleado ) REFERENCES Empleado (ID_Empleado ) ON UPDATE CASCADE ON DELETE CASCADE
    );

create table Asignacion(
    ID_Materia varchar(10) ,
    ID_Curso varchar(10),
    ID_Empleado varchar(10),
    periodoLetivo varchar(15),
    FOREIGN KEY (ID_Materia) REFERENCES Materia (ID_Materia),
    FOREIGN KEY (ID_Curso ) REFERENCES Curso (ID_Curso ),
    FOREIGN KEY (ID_Empleado ) REFERENCES Empleado (ID_Empleado) ON UPDATE CASCADE ON DELETE CASCADE
);

create table Matricula(
    NO_Matricula varchar(10),
    ID_Curso varchar(10) ,
    cedula varchar(15) ,
    periodo_Electivo varchar(25) ,
    estado varchar(10),
    PRIMARY KEY(NO_Matricula,periodo_Electivo),
    FOREIGN KEY (ID_Curso) REFERENCES Curso (ID_Curso),
    FOREIGN KEY (cedula) REFERENCES Alumnos (cedula) on delete cascade on update cascade
);

create table TelefonoEstudiante(
	telefono varchar(13) NOT NULL,
    cedula varchar(15) NOT NULL,
    foreign key (cedula) references Alumnos (cedula) on delete cascade on update cascade
);

create table TelefonoEmpleado(
	telefono varchar(13) NOT NULL,
    ID_Empleado varchar(15) NOT NULL,
    foreign key (ID_Empleado) references Empleado (ID_Empleado) on delete cascade on update cascade
);

create table Pensum(
	ID_Curso varchar(10) NOT NULL,
    ID_Materia varchar(10) NOT NULL,
    PeriodoLectivo varchar(10) NOT NULL,
    foreign key (ID_Curso) references Curso (ID_Curso), 
    foreign key (ID_Materia) references Materia (ID_Materia)
);

#DATOS EMPLEADOS
INSERT INTO Empleado VALUES ("0927852897", "0908070605","Luigi Basantes", "masculino", "Guayaquil", "1993-10-08", "direccion", "ninguna", "soltero", "bachiller","ninguno",0,"matutino");
INSERT INTO Empleado VALUES ("0927852896", "0102030405", "Luis Cruz", "masculino", "Guayaquil", "1995-04-13", "direccion", "ninguna", "Soltero", "bachiller", "ninguno", 0, "matutino");
INSERT INTO Empleado VALUES ("0927852895", "1234567890", "Juan Crow", "masculino", "Babahoyo", "1996-02-16", "direccion", "ninguna", "soltero", "bachiller","ninguno",0,"matutino");
INSERT INTO Empleado VALUES ("0927852894", "0987654321","Joe Saverio", "masculino", "Guayaquil", "1995-08-30", "direccion", "ninguno", "Soltero", "bachiller", "ninguno", 0, "matutino");
INSERT INTO Empleado VALUES ("0927852898", "0987654100","Juan Pueblo", "MASCULINO", "Babahoyo", "1990-08-30", "General Barona y Roldos", "NO", "Soltero", "Tercer Nivel", "Licenciado en Ciencias de la Educacion", 4, "Completa");
INSERT INTO Empleado VALUES ("1202086014", "1202086004","WASHBRUM TORRES ANA MARIA","FEMENINO","GUAYAQUIL","1978-04-12","ROLDOS Y GARCIA MORENO","NO","DIVORCIADO","CUARTO NIVEL","MASTER EN ADMINISTRACION DE EMPRESAS",15,"COMPLETA");

#DATOS ALUMNOS
INSERT INTO Alumnos VALUES ("0987654321","ricardo Gavino","Babahoyo","1995-02-12","ninguna","Jose Gavino","Estela Garcia","Jose Gavino", "0993467544","direccion","masculino","ninguna");
INSERT INTO Alumnos VALUES ("0987654322","Gabriela Montalvo","Guayaquil","1995-11-05","ninguna","Miguel Montalvo","Josefa Chamba","Josefa Chamba","0998734234","direccion","femenino","ninguna");
INSERT INTO Alumnos VALUES ("0987654323","Ana Paredes","Lima","1995-07-04","Saleciana","Marcos Paredes","Dolores Santana","Marcos Paredes","0996689597","direccion","femenino","ninguna");
INSERT INTO Alumnos VALUES ("0987654324","Manuel Romero","Zaruma","1995-01-02","ninguna","Pablo Romero","Carlota Pickles","Pablo Romero","0834532371","direccion","masculino","ninguna");
INSERT INTO Alumnos VALUES ("0987654325","David Cruz","Loja","1995-07-28","Catolica","Mario Cruz","Marina Montez","Mario Cruz","0813732392","direccion","femenino","ninguna");
INSERT INTO Alumnos VALUES ("0987654326","Harold Rodriguez","Guayaquil","1995-07-04","Saleciana","Roberto Rodriguez","Betsy Briones","Roberto Rodriguez","0997886572","direccion","femenino","ninguna");
INSERT INTO Alumnos VALUES ("0987654327","Pedro Neutron","Guayaquil","1995-08-21","ninguna","Eduardo Neutron","Tiffany  Snow","Tiffany  Snow","0992323234","direccion","masculino","ninguna");
INSERT INTO Alumnos VALUES ("0987654328","Andrez Brito","Guayaquil","1995-12-26","ninguna","Ramon Brito","Amy Merino","Ramon Brito","0834563478","direccion","masculino","ninguna");
INSERT INTO Alumnos VALUES ("0987654329","Carlos Montez","Guayaquil","1995-11-14","Saleciana","Guillermo Montez","Luisa Velez","Guillermo Montez","0956757890","direccion","masculino","ninguna");
INSERT INTO Alumnos VALUES ("0987654330","Jean Canela","Guayaquil","1995-03-14","ninguna","Ted Canela","Viviana Chavez","Ted Canela","0939661874","direccion","masculino","ninguna");
INSERT INTO Alumnos VALUES ("0987654331","Nelson Tarantino","Loja","1995-03-21","Catolica","Antonio Tarantino","Nicole Tapia","Antonio Tarantino","0946457479","direccion","masculino","ninguna");
INSERT INTO Alumnos VALUES ("0987654332","Ricky Martin","Guayaquil","1995-09-24","Saleciana","Enrique Martin","Elvira Moreira","Elvira Moreira","0912982376","direccion","masculino","ninguna");


INSERT INTO Alumnos VALUES ("0987654351","Alex Macas","Babahoyo","1995-02-12","ninguna","Jose Gavino","Estela Garcia","Jose Gavino", "0993467544","direccion","masculino","ninguna");
INSERT INTO Alumnos VALUES ("0987654352","Joe Zambrano","Guayaquil","1995-11-05","ninguna","Miguel Montalvo","Josefa Chamba","Josefa Chamba","0998734234","direccion","femenino","ninguna");
INSERT INTO Alumnos VALUES ("0987654353","Ana Zamora","Lima","1995-07-04","Saleciana","Marcos Paredes","Dolores Santana","Marcos Paredes","0996689597","direccion","femenino","ninguna");
INSERT INTO Alumnos VALUES ("0987654354","Manuel Leon","Zaruma","1995-01-02","ninguna","Pablo Romero","Carlota Pickles","Pablo Romero","0834532371","direccion","masculino","ninguna");
INSERT INTO Alumnos VALUES ("0987654355","David Crinstom","Loja","1995-07-28","Catolica","Mario Cruz","Marina Montez","Mario Cruz","0813732392","direccion","femenino","ninguna");
INSERT INTO Alumnos VALUES ("0987654356","Harold Moran","Guayaquil","1995-07-04","Saleciana","Roberto Rodriguez","Betsy Briones","Roberto Rodriguez","0997886572","direccion","femenino","ninguna");
INSERT INTO Alumnos VALUES ("0987654357","Pedro Jaramillo","Guayaquil","1995-08-21","ninguna","Eduardo Neutron","Tiffany  Snow","Tiffany  Snow","0992323234","direccion","masculino","ninguna");
INSERT INTO Alumnos VALUES ("0987654358","Andrez Sanchez","Guayaquil","1995-12-26","ninguna","Ramon Brito","Amy Merino","Ramon Brito","0834563478","direccion","masculino","ninguna");
INSERT INTO Alumnos VALUES ("0987654359","Carlos Montiel","Guayaquil","1995-11-14","Saleciana","Guillermo Montez","Luisa Velez","Guillermo Montez","0956757890","direccion","masculino","ninguna");
INSERT INTO Alumnos VALUES ("0987654360","Jean Carbo","Guayaquil","1995-03-14","ninguna","Ted Canela","Viviana Chavez","Ted Canela","0939661874","direccion","masculino","ninguna");
INSERT INTO Alumnos VALUES ("0987654361","Nelson Bone","Loja","1995-03-21","Catolica","Antonio Tarantino","Nicole Tapia","Antonio Tarantino","0946457479","direccion","masculino","ninguna");
INSERT INTO Alumnos VALUES ("0987654362","Ricky Minaj","Guayaquil","1995-09-24","Saleciana","Enrique Martin","Elvira Moreira","Elvira Moreira","0912982376","direccion","masculino","ninguna");
INSERT INTO Alumnos VALUES ("0987654363","Sebastian Fuenzalida","Babahoyo","1995-02-12","ninguna","Jose Gavino","Estela Garcia","Jose Gavino", "0993467544","direccion","masculino","ninguna");
INSERT INTO Alumnos VALUES ("0987654364","Angelo Reina","Guayaquil","1995-11-05","ninguna","Miguel Montalvo","Josefa Chamba","Josefa Chamba","0998734234","direccion","femenino","ninguna");
INSERT INTO Alumnos VALUES ("0987654365","Clemente Vallen","Lima","1995-07-04","Saleciana","Marcos Paredes","Dolores Santana","Marcos Paredes","0996689597","direccion","femenino","ninguna");
INSERT INTO Alumnos VALUES ("0987654366","Monica Aguilera","Zaruma","1995-01-02","ninguna","Pablo Romero","Carlota Pickles","Pablo Romero","0834532371","direccion","masculino","ninguna");
INSERT INTO Alumnos VALUES ("0987654367","Jorge Restrepo","Loja","1995-07-28","Catolica","Mario Cruz","Marina Montez","Mario Cruz","0813732392","direccion","femenino","ninguna");
INSERT INTO Alumnos VALUES ("0987654368","Gabriel Lorona","Guayaquil","1995-07-04","Saleciana","Roberto Rodriguez","Betsy Briones","Roberto Rodriguez","0997886572","direccion","femenino","ninguna");
INSERT INTO Alumnos VALUES ("0987654369","David Manzo","Guayaquil","1995-08-21","ninguna","Eduardo Neutron","Tiffany  Snow","Tiffany  Snow","0992323234","direccion","masculino","ninguna");
INSERT INTO Alumnos VALUES ("0987654370","Alex Roman","Guayaquil","1995-12-26","ninguna","Ramon Brito","Amy Merino","Ramon Brito","0834563478","direccion","masculino","ninguna");
INSERT INTO Alumnos VALUES ("0987654371","Alison Santos","Guayaquil","1995-11-14","Saleciana","Guillermo Montez","Luisa Velez","Guillermo Montez","0956757890","direccion","FEMENINO","ninguna");
INSERT INTO Alumnos VALUES ("0987654372","Sonia Reyes","Guayaquil","1995-03-14","ninguna","Ted Canela","Viviana Chavez","Ted Canela","0939661874","direccion","masculino","ninguna");
INSERT INTO Alumnos VALUES ("0987654373","Ivanna Fuentes","Loja","1995-03-21","Catolica","Antonio Tarantino","Nicole Tapia","Antonio Tarantino","0946457479","direccion","FEMENINO","ninguna");
INSERT INTO Alumnos VALUES ("0987654374","Rocky Balboa","Guayaquil","1995-09-24","Saleciana","Enrique Martin","Elvira Moreira","Elvira Moreira","0912982376","direccion","masculino","ninguna");
INSERT INTO Alumnos VALUES ("0987654375","Jorge Martinez","Loja","1995-07-28","Catolica","Mario Cruz","Marina Montez","Mario Cruz","0813732392","direccion","MASCULINO","ninguna");
INSERT INTO Alumnos VALUES ("0987654376","Carlos Martin","Guayaquil","1995-07-04","Saleciana","Roberto Rodriguez","Betsy Briones","Roberto Rodriguez","0997886572","direccion"," MASCULINO","ninguna");
INSERT INTO Alumnos VALUES ("0987654377","Adonis Romero","Guayaquil","1995-08-21","ninguna","Eduardo Neutron","Tiffany  Snow","Tiffany  Snow","0992323234","direccion","masculino","ninguna");
INSERT INTO Alumnos VALUES ("0987654378","Italo Romo","Guayaquil","1995-12-26","ninguna","Ramon Brito","Amy Merino","Ramon Brito","0834563478","direccion","masculino","ninguna");
INSERT INTO Alumnos VALUES ("0987654379","Alicia Valverde","Guayaquil","1995-11-14","Saleciana","Guillermo Montez","Luisa Velez","Guillermo Montez","0956757890","direccion","FEMENINO","ninguna");
INSERT INTO Alumnos VALUES ("0987654380","Sabrina Romo","Guayaquil","1995-03-14","ninguna","Ted Canela","Viviana Chavez","Ted Canela","0939661874","direccion","FEMENINO","ninguna");
INSERT INTO Alumnos VALUES ("0987654381","Rubina Rojas","Loja","1995-03-21","Catolica","Antonio Tarantino","Nicole Tapia","Antonio Tarantino","0946457479","direccion","FEMENINO","ninguna");
INSERT INTO Alumnos VALUES ("0987654382","Clara Velazquez","Guayaquil","1995-09-24","Saleciana","Enrique Martin","Elvira Moreira","Elvira Moreira","0912982376","direccion","masculino","ninguna");


#DATOS CUENTAS
INSERT Cuenta VALUES ("RicardoGavino","1234567890","0987654321",null);
INSERT Cuenta VALUES ("GabrielaMontalvo","1234567891","0987654322",null);
INSERT Cuenta VALUES ("AnaParedes","1234567892","0987654323",null);
INSERT Cuenta VALUES ("ManuelRomero","1234567893","0987654324",null);
INSERT Cuenta VALUES ("DavidCruz","1234567894","0987654325",null);
INSERT Cuenta VALUES ("HaroldRodriguez","1234567895","0987654326",null);
INSERT Cuenta VALUES ("JoeSaverio","1234567890",null,"0927852894");
INSERT Cuenta VALUES ("JuanCrow","1234567891",null,"0927852895");
INSERT Cuenta VALUES ("LuisCruz","1234567892",null,"0927852896");
INSERT Cuenta VALUES ("LuigiBasantes","1234567893",null,"0927852897");
INSERT Cuenta VALUES ("JuanPueblo","1234567889",null,"0927852898");
INSERT Cuenta VALUES ("AnaWashbrum","1202086004",null,"1202086014");


#DATOS CARGO
INSERT INTO Cargo VALUES("ADMN001","Rector");
INSERT INTO Cargo VALUES("ADMN002","Vicerrector");
INSERT INTO Cargo VALUES("ADMN003","Secretario");
INSERT INTO Cargo VALUES ("PROF001","Profesor de fisica");
INSERT INTO Cargo VAlUES("PROF002","Profesor de Matematica");
INSERT INTO Cargo VAlUES("PROF003","Profesor de Literatura");
INSERT INTO Cargo VAlUES("PROF004","Profesor de Ciencias Sociales");
INSERT INTO Cargo VAlUES("PROF005","Profesor de Ciencias Naturales");
INSERT INTO Cargo VAlUES("PROF006","Profesor de Quimica");
INSERT INTO Cargo VAlUES("PROF007","Profesor de Lenguaje");
INSERT INTO Cargo VAlUES("PROF008","Profesor de Ingles");
INSERT INTO Cargo VAlUES("PROF009","Profesor de Mecanica Automotriz");
INSERT INTO Cargo VAlUES("PROF010","Profesor de Desarrollo del pensamiento");
INSERT INTO Cargo VALUES("ADMN004","Ayudante de secretaria");

#DATOS DE DEPARTAMENTO
INSERT INTO Departamento VALUES ("DEP001", "Docencia", "Departamento de Docencia");
INSERT INTO Departamento VALUES("DEP002","Rectorado","Departamento de Rectorado");
INSERT INTO Departamento VALUES("DEP003","Vicerrectorado","Departamento de Vicerrectorado");
INSERT INTO Departamento VALUES("DEP004","Secretaria","Departamento de Secreteria");
INSERT INTO Departamento VALUES("DEP005","Enfermeria","Departamento de Enfermeria");

#DATOS DE CONTRATO
INSERT INTO Contrato VALUES("0927852894","DEP001","PROF003",curdate(),"2018-10-12","CKM2023","DEFINITIVO","Docente de Tercer Nivel",1200);
INSERT INTO Contrato VALUES("0927852896","DEP001","PROF004",curdate(),"2018-10-12","CKM2024","DEFINITIVO","Docente de Tercer Nivel",1200);
INSERT INTO Contrato values("0927852897","DEP001","PROF001",curdate(),"2020-12-12","ABC6666","DEFINITIVO","Docente de Segundo Nivel",800);
INSERT INTO Contrato VALUES("0927852895","DEP002","ADMN001",curdate(),"2016-10-12","CKM2040","DEFINITIVO","Administrativo de Cuarto Nivel",5000);
INSERT INTO Contrato VALUES("0927852895","DEP001","PROF002",curdate(),"2018-10-12","CKM2041","DEFINITIVO","Docente de Cuarto Nivel",1800);
INSERT INTO Contrato values("0927852897","DEP003","ADMN002",curdate(),"2020-12-12","ABC6645","DEFINITIVO","Administrativo de Tercer Nivel",5000);
INSERT INTO Contrato VALUES("1202086014","DEP004","ADMN003",curdate(),"2020-10-12","CKB2120","DEFINITIVO","Administrativo de Segundo Nivel",1800);
INSERT INTO Contrato VALUES("0927852898","DEP004","ADMN004",curdate(),"2016-11-20","CAC2021","PROVISIONAL","Administrativo de Primer Nivel",1000);

#DATOS DE CURSO
INSERT Curso VALUES("2016CUR001","SEGUNDO EGB","A","ACTIVO","2016-2017",32,"0927852897");
INSERT Curso VALUES("2016CUR002","SEGUNDO EGB","B","ACTIVO","2016-2017",32,null);
INSERT Curso VALUES("2016CUR003","SEGUNDO EGB","C","ACTIVO","2016-2017",32,null);
INSERT Curso VALUES("2016CUR004","TERCERO EGB","A","ACTIVO","2016-2017",32,null);
INSERT Curso VALUES("2016CUR005","TERCERO EGB","B","ACTIVO","2016-2017",32,null);
INSERT Curso VALUES("2016CUR006","TERCERO EGB","C","ACTIVO","2016-2017",32,null);
INSERT Curso VALUES("2016CUR007","CUARTO EGB","A","ACTIVO","2016-2017",32,null);
INSERT Curso VALUES("2016CUR008","CUARTO EGB","B","ACTIVO","2016-2017",32,null);
INSERT Curso VALUES("2016CUR009","CUARTO EGB","C","ACTIVO","2016-2017",32,null);
INSERT Curso VALUES("2016CUR010","QUINTO EGB","A","ACTIVO","2016-2017",32,null);
INSERT Curso VALUES("2016CUR011","QUINTO EGB","B","ACTIVO","2016-2017",32,null);
INSERT Curso VALUES("2016CUR012","QUINTO EGB","C","ACTIVO","2016-2017",32,null);
INSERT Curso VALUES("2016CUR013","SEXTO EGB","A","ACTIVO","2016-2017",32,null);
INSERT Curso VALUES("2016CUR014","SEXTO EGB","B","ACTIVO","2016-2017",32,null);
INSERT Curso VALUES("2016CUR015","SEXTO EGB","C","ACTIVO","2016-2017",32,null);
INSERT Curso VALUES("2016CUR016","SEPTIMO EGB","A","ACTIVO","2016-2017",32,null);
INSERT Curso VALUES("2016CUR017","SEPTIMO EGB","B","ACTIVO","2016-2017",32,null);
INSERT Curso VALUES("2016CUR018","SEPTIMO EGB","C","ACTIVO","2016-2017",32,null);
INSERT Curso VALUES("2016CUR019","OCTAVO EGB","A","ACTIVO","2016-2017",32,null);
INSERT Curso VALUES("2016CUR020","OCTAVO EGB","B","ACTIVO","2016-2017",32,null);
INSERT Curso VALUES("2016CUR021","OCTAVO EGB","C","ACTIVO","2016-2017",32,null);
INSERT Curso VALUES("2016CUR022","NOVENO EGB","A","ACTIVO","2016-2017",32,null);
INSERT Curso VALUES("2016CUR023","NOVENO EGB","B","ACTIVO","2016-2017",32,null);
INSERT Curso VALUES("2016CUR024","NOVENO EGB","C","ACTIVO","2016-2017",32,null);
INSERT Curso VALUES("2016CUR025","DECIMO EGB","A","ACTIVO","2016-2017",32,null);
INSERT Curso VALUES("2016CUR026","DECIMO EGB","B","ACTIVO","2016-2017",32,null);
INSERT Curso VALUES("2016CUR027","DECIMO EGB","C","ACTIVO","2016-2017",32,null);
INSERT Curso VALUES("2016CUR028","DECIMO EGB","D","INACTIVO","2016-2017",32,null);
INSERT Curso VALUES("2016CUR029","PRIMERO BACHILLERATO","A","ACTIVO","2016-2017",32,"0927852894");
INSERT Curso VALUES("2016CUR030","PRIMERO BACHILLERATO","B","ACTIVO","2016-2017",32,"0927852896");
INSERT Curso VALUES("2016CUR031","SEGUNDO BACHILLERATO","A","ACTIVO","2016-2017",32,null);
INSERT Curso VALUES("2016CUR032","SEGUNDO BACHILLERATO","B","ACTIVO","2016-2017",32,null);
INSERT Curso VALUES("2016CUR033","TERCERO BACHILLERATO","A","ACTIVO","2016-2017",32,null);
INSERT Curso VALUES("2016CUR034","TERCERO BACHILLERATO","B","ACTIVO","2016-2017",32,null);
INSERT Curso VALUES("2015CUR001","SEGUNDO EGB","A","ACTIVO","2015-2016",32,null);
INSERT Curso VALUES("2015CUR002","SEGUNDO EGB","B","ACTIVO","2015-2016",32,null);
INSERT Curso VALUES("2015CUR003","SEGUNDO EGB","C","ACTIVO","2015-2016",32,null);
INSERT Curso VALUES("2015CUR004","TERCERO EGB","A","ACTIVO","2015-2016",32,null);
INSERT Curso VALUES("2015CUR005","TERCERO EGB","B","ACTIVO","2015-2016",32,null);
INSERT Curso VALUES("2015CUR006","TERCB","C","ACTIVO","2015-2016",32,null);
INSERT Curso VALUES("2015CUR007","CUARTO EGB","A","ACTIVO","2015-2016",32,null);
INSERT Curso VALUES("2015CUR008","CUARTO EGB","B","ACTIVO","2015-2016",32,null);
INSERT Curso VALUES("2015CUR009","CUARTO EGB","C","ACTIVO","2015-2016",32,null);
INSERT Curso VALUES("2015CUR010","QUINTO EGB","A","ACTIVO","2015-2016",32,null);
INSERT Curso VALUES("2015CUR011","QUINTO EGB","B","ACTIVO","2015-2016",32,null);
INSERT Curso VALUES("2015CUR012","QUINTO EGB","C","ACTIVO","2015-2016",32,null);
INSERT Curso VALUES("2015CUR013","SEXTO EGB","A","ACTIVO","2015-2016",32,null);
INSERT Curso VALUES("2015CUR014","SEXTO EGB","B","ACTIVO","2015-2016",32,null);
INSERT Curso VALUES("2015CUR015","SEXTO EGB","C","ACTIVO","2015-2016",32,null);


#DATOS DE MATRICULA
INSERT matricula VALUES("MAT0001","2016CUR029","0987654323","2016-2017","ACTIVO");
INSERT matricula VALUES("MAT0002","2016CUR029","0987654324","2016-2017","ACTIVO");
INSERT matricula VALUES("MAT0003","2016CUR003","0987654325","2016-2017","ACTIVO");
INSERT matricula VALUES("MAT0004","2016CUR025","0987654326","2016-2017","ACTIVO");
INSERT matricula VALUES("MAT0005","2016CUR029","0987654327","2016-2017","ACTIVO");
INSERT matricula VALUES("MAT0006","2016CUR029","0987654328","2016-2017","ACTIVO");
INSERT matricula VALUES("MAT0007","2016CUR029","0987654329","2016-2017","ACTIVO");
INSERT matricula VALUES("MAT0008","2016CUR029","0987654330","2016-2017","ACTIVO");
INSERT matricula VALUES("MAT0009","2016CUR029","0987654331","2016-2017","ACTIVO");
INSERT matricula VALUES("MAT0010","2016CUR029","0987654332","2016-2017","ACTIVO");

INSERT matricula VALUES("MAT0011","2016CUR030","0987654351","2016-2017","ACTIVO");
INSERT matricula VALUES("MAT0012","2016CUR030","0987654352","2016-2017","ACTIVO");
INSERT matricula VALUES("MAT0013","2016CUR030","0987654353","2016-2017","ACTIVO");
INSERT matricula VALUES("MAT0014","2016CUR030","0987654354","2016-2017","ACTIVO");
INSERT matricula VALUES("MAT0015","2016CUR030","0987654355","2016-2017","ACTIVO");
INSERT matricula VALUES("MAT0016","2016CUR030","0987654356","2016-2017","ACTIVO");
INSERT matricula VALUES("MAT0017","2016CUR030","0987654357","2016-2017","ACTIVO");
INSERT matricula VALUES("MAT0018","2016CUR030","0987654358","2016-2017","ACTIVO");
INSERT matricula VALUES("MAT0019","2016CUR030","0987654359","2016-2017","ACTIVO");
INSERT matricula VALUES("MAT0020","2016CUR030","0987654360","2016-2017","ACTIVO");
INSERT matricula VALUES("MAT0021","2016CUR030","0987654361","2016-2017","ACTIVO");
INSERT matricula VALUES("MAT0022","2016CUR030","0987654362","2016-2017","ACTIVO");
INSERT matricula VALUES("MAT0023","2016CUR030","0987654363","2016-2017","ACTIVO");
INSERT matricula VALUES("MAT0024","2016CUR030","0987654364","2016-2017","ACTIVO");
INSERT matricula VALUES("MAT0025","2016CUR030","0987654365","2016-2017","ACTIVO");
INSERT matricula VALUES("MAT0026","2016CUR030","0987654366","2016-2017","ACTIVO");
INSERT matricula VALUES("MAT0027","2016CUR030","0987654367","2016-2017","ACTIVO");
INSERT matricula VALUES("MAT0028","2016CUR030","0987654368","2016-2017","ACTIVO");
INSERT matricula VALUES("MAT0029","2016CUR030","0987654369","2016-2017","ACTIVO");
INSERT matricula VALUES("MAT0030","2016CUR030","0987654370","2016-2017","ACTIVO");
INSERT matricula VALUES("MAT0031","2016CUR030","0987654371","2016-2017","ACTIVO");
INSERT matricula VALUES("MAT0032","2016CUR030","0987654372","2016-2017","ACTIVO");
INSERT matricula VALUES("MAT0033","2016CUR030","0987654373","2016-2017","ACTIVO");
INSERT matricula VALUES("MAT0034","2016CUR030","0987654374","2016-2017","ACTIVO");
INSERT matricula VALUES("MAT0035","2016CUR030","0987654375","2016-2017","ACTIVO");
INSERT matricula VALUES("MAT0036","2016CUR030","0987654376","2016-2017","ACTIVO");
INSERT matricula VALUES("MAT0037","2016CUR030","0987654377","2016-2017","ACTIVO");
INSERT matricula VALUES("MAT0038","2016CUR030","0987654378","2016-2017","ACTIVO");
INSERT matricula VALUES("MAT0039","2016CUR030","0987654379","2016-2017","ACTIVO");
INSERT matricula VALUES("MAT0040","2016CUR030","0987654380","2016-2017","ACTIVO");
INSERT matricula VALUES("MAT0041","2016CUR030","0987654381","2016-2017","ACTIVO");
INSERT matricula VALUES("MAT0042","2016CUR030","0987654382","2016-2017","ACTIVO");

INSERT matricula VALUES("MAT0043","2016CUR001","0987654382","2016-2017","ACTIVO");

#DATOS DE MATERIAS
INSERT Materia Values("2016INGL01","INGLES BACHILLERATO","ACTIVO");
INSERT Materia Values("2016MATE02","MATEMATICA BACHILLERATO","ACTIVO");
INSERT Materia Values("2016LITR01","LITERATURA","ACTIVO");
INSERT Materia Values("2016FISC02","FISICA BACHILLERATO","ACTIVO");
INSERT Materia Values("2016SOCI02","CIENCIAS SOCIALES (BACH)","ACTIVO");
INSERT Materia Values("2016SOCI01","CIENCIAS SOCIALES (EGB)","ACTIVO");
INSERT Materia Values("2016QUMC02","QUIMICA (BACH)","ACTIVO");
INSERT Materia Values("2016QUMC01","QUIMICA (EGB)","ACTIVO");
INSERT Materia Values("2016DESP01","DESARROLLO DEL PENSAMIENTO","ACTIVO");
INSERT Materia Values("2016FRAN01","FRANCES BASICO","INACTIVO");
INSERT Materia Values("2016MATE01","MATEMATICA","ACTIVO");
INSERT Materia Values("2016LENG01","LENGUAJE Y COMUNICACION","ACTIVO");

#DATOS DE PENSUM
INSERT pensum VALUES("2016CUR029","2016MATE02","2016-2017");
INSERT pensum VALUES("2016CUR029","2016LITR01","2016-2017");
INSERT pensum VALUES("2016CUR029","2016FISC02","2016-2017");
INSERT pensum VALUES("2016CUR029","2016INGL01","2016-2017");
INSERT pensum VALUES("2016CUR029","2016SOCI02","2016-2017");
INSERT pensum VALUES("2016CUR029","2016QUMC02","2016-2017");
INSERT pensum VALUES("2016CUR029","2016DESP01","2016-2017");
INSERT pensum VALUES("2016CUR029","2016FRAN01","2016-2017");

INSERT pensum VALUES("2016CUR030","2016MATE02","2016-2017");
INSERT pensum VALUES("2016CUR030","2016LITR01","2016-2017");
INSERT pensum VALUES("2016CUR030","2016FISC02","2016-2017");
INSERT pensum VALUES("2016CUR030","2016INGL01","2016-2017");
INSERT pensum VALUES("2016CUR030","2016SCOI02","2016-2017");
INSERT pensum VALUES("2016CUR030","2016QUMC02","2016-2017");
INSERT pensum VALUES("2016CUR030","2016DESP01","2016-2017");
INSERT pensum VALUES("2016CUR030","2016FRAN01","2016-2017");

INSERT pensum VALUES("2016CUR031","2016MATE02","2016-2017");
INSERT pensum VALUES("2016CUR031","2016LITR01","2016-2017");
INSERT pensum VALUES("2016CUR031","2016FISC02","2016-2017");
INSERT pensum VALUES("2016CUR031","2016INGL01","2016-2017");
INSERT pensum VALUES("2016CUR031","2016SCOI02","2016-2017");
INSERT pensum VALUES("2016CUR031","2016QUMC02","2016-2017");
INSERT pensum VALUES("2016CUR031","2016DESP01","2016-2017");
INSERT pensum VALUES("2016CUR031","2016FRAN01","2016-2017");

INSERT pensum VALUES("2016CUR031","2016FRAN01","2016-2017");
INSERT pensum VALUES("2016CUR031","2016LENG01","2016-2017");
INSERT pensum VALUES("2016CUR031","2016SOCI02","2016-2017");
INSERT pensum VALUES("2016CUR031","2016MATE02","2016-2017");
INSERT pensum VALUES("2016CUR031","2016QUMC02","2016-2017");
INSERT pensum VALUES("2016CUR031","2016INGL01","2016-2017");
INSERT pensum VALUES("2016CUR031","2016FISC02","2016-2017");

SELECT * FROM materia;
SELECT * FROM CURSO;

#DATOS DE ASIGNACION
INSERT Asignacion VALUES("2016LITR01","2016CUR029","0927852894","2016-2017");
INSERT Asignacion VALUES("2016SOCI02","2016CUR029","0927852896","2016-2017");
INSERT Asignacion VALUES("2016FISC02","2016CUR029","0927852897","2016-2017");
INSERT Asignacion VALUES("2016MATE02","2016CUR029","0927852895","2016-2017");

INSERT Asignacion VALUES("2016LENG01","2015CUR001","0927852898","2016-2017");
INSERT Asignacion VALUES("2016SOCI01","2015CUR001","0927852896","2016-2017");

#DATOS DE NOTAS
INSERT Libreta VALUES("0987654323","2016FISC02","0927852897",10,8,0,9);
INSERT Libreta VALUES("0987654323","2016SOCI02","0927852896",9,7,0,8);
INSERT Libreta VALUES("0987654323","2016LITR01","0927852894",8,9,0,8.5);
INSERT Libreta VALUES("0987654323","2016MATE02","0927852895",7,5,7,7);
INSERT Libreta VALUES("0987654324","2016FISC02","0927852897",7,5,0,6);
INSERT Libreta VALUES("0987654327","2016FISC02","0927852897",5,6,0,5.5);
INSERT Libreta VALUES("0987654328","2016FISC02","0927852897",6,10,0,8);

#DATOS DE TELEFONOS DE EMPLEADOS
INSERT telefonoempleado VALUES("0981488883","0927852895");
INSERT telefonoempleado VALUES("052731855","0927852895");

#DATOS DE TELEFONOS DE ESTUDIANTES
INSERT INTO telefonoestudiante VALUES("11111111","0987654323");
INSERT INTO telefonoestudiante VALUES("22222222","0987654323");


#VISTAS
CREATE VIEW ultimoCargo AS
(Select CAST((Select substring(max(Cargo.ID_Cargo),-3)) AS UNSIGNED) FROM Cargo);



/*QUERYS GENERALES*/
Select * FROM Cuenta;
SELECT * FROM Empleado;
SELECT * FROM Matricula;
SELECT * FROM telefonoestudiante;
SELECT * FROM telefonoempleado;
SELECT * FROM Curso;
select * from Empleado;
SELECT * FROM ALUMNOS;
select * from Departamento;
Select * from Contrato;
Select * from Cargo;
Select * from Materia;
Select * from pensum;
Select * from asignacion;
Select * from Libreta;
Select * FROM Empleado JOIN Contrato ON Empleado.ID_Empleado=Contrato.Empleado JOIN Cargo ON Cargo.ID_Cargo=Contrato.Cargo;
/*QUERYS GENERALES*/

ALTER TABLE Libreta DROP COLUMN Libreta.ID_Libreta;
ALTER TABLE Libreta DROP COLUMN Libreta.examen_Recuperacion;
ALTER TABLE Empleado DROP COLUMN Empleado.telefono;

#NO EJECUTAR
UPDATE Contrato SET codigoNombramiento="CKM2024" WHERE Empleado="0927852896";
ALTER TABLE Matricula ADD COLUMN Estado varchar(10);
UPDATE Matricula SET Matricula.periodo_Electivo="2016-2017";
ALTER TABLE Contrato modify COLUMN Contrato.codigoNombramiento varchar(7) PRIMARY KEY;
Select a.*,m.NO_Matricula from Alumnos a,Matricula m where a.cedula=m.cedula AND a.nombreA="Ana Paredes";
ALTER TABLE Alumnos CHANGE telefono telefono_Representante VARCHAR(12);
DELETE FROM BD_Colegio.telefonoestudiante where cedula='0987654323';
ALTER TABLE Materia MODIFY COLUMN nombreM varchar(50);
ALTER TABLE pensum add column PeriodoLectivo varchar(10);
UPDATE pensum SET PeriodoLectivo="2016-2017";

/*NO EJECUTAR ESTO*/
Alter Table Cuenta drop foreign key cuenta_ibfk_1;
Alter Table Cuenta add FOREIGN KEY (cedula) REFERENCES Alumnos (cedula) on delete cascade on update cascade;
Alter table libreta drop foreign key libreta_ibfk_1;
Alter table Libreta add FOREIGN KEY (cedula) REFERENCES Alumnos (cedula) on delete cascade on update cascade;
Alter table Matricula drop foreign key matricula_ibfk_2;
Alter table Matricula add FOREIGN KEY (cedula) REFERENCES Alumnos (cedula) on delete cascade on update cascade;
Alter table telefonoestudiante drop foreign key telefonoestudiante_ibfk_1;
Alter table telefonoestudiante add FOREIGN KEY (cedula) REFERENCES Alumnos (cedula) on delete cascade on update cascade;

SELECT * FROM matricula;
SELECT * FROM Alumnos;
SELECT * FROM curso;
SELECT * FROM pensum;
select descripcion from Departamento;

SELECT m.NO_Matricula, a.nombreA, l.nota1, l.nota2, l.notaSup FROM alumnos a, libreta l, matricula m, materia ma WHERE a.cedula = m.cedula AND a.cedula = l.cedula AND ma.ID_Materia = l.ID_Materia AND ma.nombreM = "MATEMATICA BACHILLERATO" AND m.ID_Curso = (SELECT ID_Curso FROM curso WHERE nombreC = "PRIMERO BACHILLERATO" AND paralelo = "A" AND periodoLectivo = "2016-2017");

SELECT  DISTINCT m.nombreM,m.ID_Materia FROM Curso c JOIN Materia m JOIN pensum p ON c.ID_Curso = p.ID_Curso AND m.ID_Materia = p.ID_Materia AND c.nombreC ="PRIMERO BACHILLERATO" AND c.periodoLectivo = "2016-2017";
Select c.ID_Curso FROM Curso c JOIN Matricula m ON c.ID_Curso=m.ID_Curso WHERE m.cedula="1207716810" AND m.periodo_Electivo="2016-2017";
Select Asignacion.ID_Empleado FROM Asignacion WHERE Asignacion.ID_Curso="2016CUR029" AND Asignacion.ID_Materia="2016FISC02" AND Asignacion.periodoLetivo="2016-2017";


DELETE FROM Contrato WHERE Contrato.Empleado="0927852896" AND Contrato.cargo="PROF011";
DELETE FROM Alumnos WHERE cedula="1207716810";