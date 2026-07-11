package com.proyecto.sistema_citas_poli.DAO;

import com.proyecto.sistema_citas_poli.Model.Persona;

import java.util.List;

public interface PersonaDAO {


    boolean crear(Persona persona);


    Persona login(
            String correo,
            String contrasena
    );


    List<Persona> listarTodos();


    List<Persona> buscar(
            String texto
    );


    Persona buscarPorId(
            int id
    );


    int contarUsuarios();


    boolean actualizar(
            Persona persona
    );


    boolean eliminar(
            int id
    );


}