package com.proyecto.sistema_citas_poli.Model;


public class Medico extends Persona {


    private int medicoId;

    private String especialidad;



    public Medico(int id,
                  String nombre,
                  String apellido,
                  String correo,
                  String contrasena,
                  String rol,
                  String telefono,
                  boolean activo,
                  int medicoId,
                  String especialidad) {


        super(id,
                nombre,
                apellido,
                correo,
                contrasena,
                rol,
                telefono,
                activo);



        this.medicoId = medicoId;

        this.especialidad = especialidad;


    }




    public int getMedicoId() {

        return medicoId;

    }



    public void setMedicoId(int medicoId) {

        this.medicoId = medicoId;

    }




    public String getEspecialidad() {

        return especialidad;

    }




    public void setEspecialidad(String especialidad) {

        this.especialidad = especialidad;

    }





    @Override
    public String toString() {


        return "Dr. "
                + getNombre()
                + " "
                + getApellido()
                + " - "
                + especialidad;

    }


}