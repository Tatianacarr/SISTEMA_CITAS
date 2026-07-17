package com.proyecto.sistema_citas_poli.Model;

public class Administrador extends Persona {

    public Administrador(int id,
                         String nombre,
                         String apellido,
                         String correo,
                         String contrasena,
                         String rol,
                         String telefono,
                         boolean activo) {

        super(id,
                nombre,
                apellido,
                correo,
                contrasena,
                rol,
                telefono,
                activo);
    }

    @Override
    public String toString() {
        return getNombre() + " " + getApellido();
    }

}