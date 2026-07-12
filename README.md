
# Sistema de Citas Médicas POLI

## Descripción

Sistema de escritorio desarrollado en Java con JavaFX y PostgreSQL para la gestión de citas médicas. La aplicación permite administrar usuarios, médicos, pacientes y citas mediante diferentes módulos según el rol del usuario.

El sistema fue desarrollado siguiendo una arquitectura por capas utilizando el patrón DAO para separar la lógica de acceso a datos de la lógica de la aplicación.

## Funcionalidades

### Administrador

* Inicio de sesión.
* Visualización de usuarios registrados.
* Búsqueda de usuarios.
* Registro de nuevos usuarios.
* Edición de usuarios.
* Eliminación de usuarios.
* Visualización de médicos y pacientes.
* Administración general del sistema.
* Cierre de sesión.

### Médico

* Inicio de sesión.
* Visualización de las citas asignadas.
* Consulta de los pacientes que agendaron una cita con él.
* Cambio del estado de las citas.
* Registro de observaciones durante la atención médica.
* Cierre de sesión.

### Paciente

* Registro en el sistema.
* Inicio de sesión.
* Agendamiento de citas.
* Selección de médico y especialidad.
* Consulta de sus citas.
* Modificación de fecha y hora de una cita.
* Cancelación de citas.
* Eliminación de citas.
* Cierre de sesión.

## Tecnologías utilizadas

* Java 21
* JavaFX
* PostgreSQL
* JDBC
* IntelliJ IDEA
* Scene Builder
* Git
* GitHub

## Arquitectura del proyecto

El proyecto está organizado en los siguientes paquetes:

* App
* BaseDatos
* Controller
* DAO
* Model
* Util

Se implementó el patrón DAO para realizar las operaciones de acceso a la base de datos.

## Base de datos

La base de datos está desarrollada en PostgreSQL y contiene las tablas principales:

* usuario
* medico
* citas

Estas tablas permiten administrar la información de los usuarios, los médicos registrados y las citas médicas.

## Requisitos

Antes de ejecutar el proyecto es necesario contar con:

* JDK 21 o superior.
* PostgreSQL instalado y configurado.
* IntelliJ IDEA.
* JavaFX SDK.

## Instalación

1. Clonar el repositorio.

```bash
git clone https://github.com/USUARIO/Sistema_Citas_Poli.git
```

2. Abrir el proyecto en IntelliJ IDEA.

3. Crear la base de datos en PostgreSQL.

4. Configurar la conexión en la clase `Conexion.java`.

5. Ejecutar la clase principal `HelloApplication`.

## Estructura del proyecto

```
Sistema_Citas_Poli
│
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com.proyecto.sistema_citas_poli
│   │   │       ├── App
│   │   │       ├── BaseDatos
│   │   │       ├── Controller
│   │   │       ├── DAO
│   │   │       ├── Model
│   │   │       └── Util
│   │   └── resources
│   └── test
│
├── pom.xml
└── README.md
```

## Autores
Karen Lozano

Angui Fierro

## Observaciones

Este proyecto fue desarrollado con fines académicos para demostrar el uso de JavaFX, PostgreSQL, JDBC y el patrón DAO en la implementación de un sistema de gestión de citas médicas.
