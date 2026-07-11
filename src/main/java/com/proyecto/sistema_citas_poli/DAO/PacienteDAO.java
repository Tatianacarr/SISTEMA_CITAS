package com.proyecto.sistema_citas_poli.DAO;

import com.proyecto.sistema_citas_poli.Model.Paciente;

import java.util.List;


public interface PacienteDAO {

    boolean crear(Paciente paciente);
    List<Paciente> listarTodos();
    Paciente buscarPorId(int id);
    boolean actualizar(Paciente paciente);
    boolean eliminar(int id);


}