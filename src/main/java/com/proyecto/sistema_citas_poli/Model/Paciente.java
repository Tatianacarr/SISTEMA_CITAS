package com.proyecto.sistema_citas_poli.Model;

public class Paciente extends Persona {

    private String historialClinico;

    public Paciente(int id,
                    String nombre,
                    String apellido,
                    String correo,
                    String contrasena,
                    String rol,
                    String telefono,
                    boolean activo,
                    String historialClinico) {

        super(id,
                nombre,
                apellido,
                correo,
                contrasena,
                rol,
                telefono,
                activo);

        this.historialClinico = historialClinico;
    }

    public String getHistorialClinico() {
        return historialClinico;
    }

    public void setHistorialClinico(String historialClinico) {
        this.historialClinico = historialClinico;
    }

    @Override
    public String toString() {
        return getNombre() + " " + getApellido();
    }
}