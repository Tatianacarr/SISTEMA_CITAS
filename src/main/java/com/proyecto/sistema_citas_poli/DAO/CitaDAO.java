package com.proyecto.sistema_citas_poli.DAO;

import com.proyecto.sistema_citas_poli.Model.Cita;

import java.util.List;

public interface CitaDAO {

    boolean agendar(Cita cita);
    List<Cita> listarPorUsuario(int usuarioId);
    List<Cita> listarTodas();
    List<Cita> listarPorMedico(int medicoId);

    boolean actualizarEstado(int idCita, String nuevoEstado);
    boolean eliminar(int idCita);


}