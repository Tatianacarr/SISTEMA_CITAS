package com.proyecto.sistema_citas_poli.Model;

import java.sql.Date;
import java.sql.Time;

public class Cita {

    private int id;
    private int pacienteId;
    private int medicoId;

    private String paciente;
    private String medico;
    private String especialidad;

    private Date fecha;
    private Time hora;

    private String estado;
    private String observacion;


    public Cita(int id,
                int pacienteId,
                int medicoId,
                String paciente,
                String medico,
                String especialidad,
                Date fecha,
                Time hora,
                String estado,
                String observacion) {


        this.id = id;
        this.pacienteId = pacienteId;
        this.medicoId = medicoId;
        this.paciente = paciente;
        this.medico = medico;
        this.especialidad = especialidad;
        this.fecha = fecha;
        this.hora = hora;
        this.estado = estado;
        this.observacion = observacion;

    }



    public int getId() {
        return id;
    }


    public int getPacienteId() {
        return pacienteId;
    }


    public int getMedicoId() {
        return medicoId;
    }


    public String getPaciente() {
        return paciente;
    }


    public String getMedico() {
        return medico;
    }


    public String getEspecialidad() {
        return especialidad;
    }


    public Date getFecha() {
        return fecha;
    }


    public Time getHora() {
        return hora;
    }


    public String getEstado() {
        return estado;
    }


    public String getObservacion() {
        return observacion;
    }



    public void setFecha(Date fecha){
        this.fecha = fecha;
    }


    public void setHora(Time hora){
        this.hora = hora;
    }


    public void setEstado(String estado){
        this.estado = estado;
    }


    public void setObservacion(String observacion){
        this.observacion = observacion;
    }



}