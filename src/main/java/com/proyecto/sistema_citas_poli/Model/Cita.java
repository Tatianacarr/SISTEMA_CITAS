package com.proyecto.sistema_citas_poli.Model;

import java.sql.Date;
import java.sql.Time;

public class Cita {

    private int id;
    private int pacienteId;
    private int medicoId;
    private String medico;
    private String especialidad;
    private Date fecha;
    private Time hora;
    private String estado;

    public Cita(int id,
                int pacienteId,
                int medicoId,
                String medico,
                String especialidad,
                Date fecha,
                Time hora,
                String estado) {

        this.id = id;
        this.pacienteId = pacienteId;
        this.medicoId = medicoId;
        this.medico = medico;
        this.especialidad = especialidad;
        this.fecha = fecha;
        this.hora = hora;
        this.estado = estado;
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

    public void setId(int id) {
        this.id = id;
    }

    public void setPacienteId(int pacienteId) {
        this.pacienteId = pacienteId;
    }

    public void setMedicoId(int medicoId) {
        this.medicoId = medicoId;
    }

    public void setMedico(String medico) {
        this.medico = medico;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public void setHora(Time hora) {
        this.hora = hora;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return medico + " - " + especialidad;
    }
}