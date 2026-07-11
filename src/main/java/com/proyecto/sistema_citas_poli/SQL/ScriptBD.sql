CREATE TABLE usuario(

                        id SERIAL PRIMARY KEY,

                        nombre VARCHAR(50) NOT NULL,

                        apellido VARCHAR(50) NOT NULL,

                        correo VARCHAR(100) UNIQUE NOT NULL,

                        contrasena VARCHAR(100) NOT NULL,

                        rol VARCHAR(30) NOT NULL,

                        telefono VARCHAR(20),

                        activo BOOLEAN DEFAULT TRUE

);



-- =====================================
-- TABLA PACIENTE
-- =====================================

CREATE TABLE paciente(

                         id SERIAL PRIMARY KEY,

                         usuario_id INT NOT NULL,

                         historial_clinico TEXT,


                         CONSTRAINT fk_paciente_usuario

                             FOREIGN KEY(usuario_id)

                                 REFERENCES usuario(id)

                                 ON DELETE CASCADE

);



-- =====================================
-- TABLA MEDICO
-- =====================================

CREATE TABLE medico(

                       id SERIAL PRIMARY KEY,

                       usuario_id INT NOT NULL,

                       especialidad VARCHAR(100) NOT NULL,


                       CONSTRAINT fk_medico_usuario

                           FOREIGN KEY(usuario_id)

                               REFERENCES usuario(id)

                               ON DELETE CASCADE

);



-- =====================================
-- TABLA CITAS
-- =====================================

CREATE TABLE citas(

                      id SERIAL PRIMARY KEY,


                      usuario_id INT NOT NULL,


                      medico_id INT NOT NULL,


                      especialidad VARCHAR(100),


                      medico VARCHAR(100),


                      fecha DATE NOT NULL,


                      hora TIME NOT NULL,


                      estado VARCHAR(30) DEFAULT 'Pendiente',


                      descripcion TEXT,


                      diagnostico TEXT,


                      receta TEXT,



                      CONSTRAINT fk_cita_usuario

                          FOREIGN KEY(usuario_id)

                              REFERENCES usuario(id)

                              ON DELETE CASCADE,



                      CONSTRAINT fk_cita_medico

                          FOREIGN KEY(medico_id)

                              REFERENCES medico(id)

                              ON DELETE CASCADE

);




-- =====================================
-- USUARIO ADMINISTRADOR INICIAL
-- =====================================


INSERT INTO usuario
(
    nombre,
    apellido,
    correo,
    contrasena,
    rol,
    telefono,
    activo
)
VALUES
    (
        'Administrador',
        'Sistema',
        'admin@medicitas.com',
        'Admin123',
        'ADMINISTRADOR',
        '0999999999',
        true
    );



-- =====================================
-- PACIENTE DE PRUEBA
-- =====================================


INSERT INTO usuario
(
    nombre,
    apellido,
    correo,
    contrasena,
    rol,
    telefono
)
VALUES
    (
        'Juan',
        'Perez',
        'juan@gmail.com',
        'Paciente123',
        'PACIENTE',
        '0988888888'
    );



INSERT INTO paciente
(
    usuario_id,
    historial_clinico
)
VALUES
    (
        2,
        'Sin antecedentes médicos'
    );



-- =====================================
-- MEDICO DE PRUEBA
-- =====================================


INSERT INTO usuario
(
    nombre,
    apellido,
    correo,
    contrasena,
    rol,
    telefono
)
VALUES
    (
        'Ana',
        'Martinez',
        'ana@medicitas.com',
        'Medico123',
        'MEDICO',
        '0977777777'
    );



INSERT INTO medico
(
    usuario_id,
    especialidad
)
VALUES
    (
        3,
        'Cardiología'
    );



-- =====================================
-- CONSULTAS DE PRUEBA
-- =====================================


SELECT * FROM usuario;

SELECT * FROM paciente;

SELECT * FROM medico;

SELECT * FROM citas;