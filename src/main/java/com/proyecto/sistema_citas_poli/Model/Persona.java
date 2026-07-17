package com.proyecto.sistema_citas_poli.Model;

public abstract class Persona {

    private int id;
    private String nombre;
    private String apellido;
    private String correo;
    private String contrasena;
    private String rol;
    private String telefono;
    private boolean activo;

    public Persona(int id,
                   String nombre,
                   String apellido,
                   String correo,
                   String contrasena,
                   String rol,
                   String telefono,
                   boolean activo) {

        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.contrasena = contrasena;
        this.rol = rol;
        this.telefono = telefono;
        this.activo = activo;
    }

    //==========================
    // GETTERS
    //==========================

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getCorreo() {
        return correo;
    }

    public String getContrasena() {
        return contrasena;
    }

    public String getRol() {
        return rol;
    }

    public String getTelefono() {
        return telefono;
    }

    public boolean isActivo() {
        return activo;
    }

    //==========================
    // SETTERS
    //==========================

    public void setId(int id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    @Override
    public String toString() {
        return nombre + " " + apellido;
    }

}