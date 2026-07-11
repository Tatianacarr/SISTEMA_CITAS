package com.proyecto.sistema_citas_poli.DAO;

import com.proyecto.sistema_citas_poli.Model.Medico;

import java.util.List;

public interface MedicoDAO {


    boolean crear(Medico medico);


    List<Medico> leerTodos();


    Medico buscarPorId(int id);


    List<Medico> buscarPorEspecialidad(String especialidad);


    boolean actualizar(Medico medico);


    boolean eliminar(int id);


}