package com.proyecto.sistema_citas_poli.DAO;

import com.proyecto.sistema_citas_poli.Model.Cita;

import java.util.List;

public interface CitaDAO {


    // Guardar una nueva cita
    boolean agendar(Cita cita);



    // Mostrar solamente las citas del paciente logueado
    List<Cita> listarPorUsuario(int usuarioId);



    // Mostrar todas las citas (Administrador)
    List<Cita> listarTodas();



    // Mostrar citas del médico
    List<Cita> listarPorMedico(int medicoId);




    // Cambiar estado: Pendiente, Cancelada, Atendida
    boolean actualizarEstado(
            int idCita,
            String nuevoEstado
    );




    // Eliminar cita
    boolean eliminar(
            int idCita
    );




    // Editar una cita existente
    boolean actualizar(
            Cita cita
    );


}