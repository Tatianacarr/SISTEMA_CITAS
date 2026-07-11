package com.proyecto.sistema_citas_poli.DAO;


import com.proyecto.sistema_citas_poli.BaseDatos.Conexion;
import com.proyecto.sistema_citas_poli.Model.Cita;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class CitaDAOImpl implements CitaDAO {



    @Override
    public boolean agendar(Cita cita) {


        String sql = """
                INSERT INTO citas
                (
                    usuario_id,
                    medico_id,
                    especialidad,
                    medico,
                    fecha,
                    hora,
                    estado
                )
                VALUES(?,?,?,?,?,?,?)
                """;


        try(Connection cn = Conexion.getConexion();
            PreparedStatement ps = cn.prepareStatement(sql)){



            ps.setInt(
                    1,
                    cita.getPacienteId()
            );


            ps.setInt(
                    2,
                    cita.getMedicoId()
            );


            ps.setString(
                    3,
                    cita.getEspecialidad()
            );


            ps.setString(
                    4,
                    cita.getMedico()
            );


            ps.setDate(
                    5,
                    cita.getFecha()
            );


            ps.setTime(
                    6,
                    cita.getHora()
            );


            ps.setString(
                    7,
                    cita.getEstado()
            );



            return ps.executeUpdate() > 0;



        }catch(SQLException e){


            System.out.println(
                    "Error agendando cita: "
                            + e.getMessage()
            );


            return false;

        }

    }










    @Override
    public List<Cita> listarPorUsuario(int usuarioId){



        List<Cita> lista =
                new ArrayList<>();



        String sql = """
                SELECT *
                FROM citas
                WHERE usuario_id=?
                ORDER BY fecha,hora
                """;



        try(Connection cn = Conexion.getConexion();

            PreparedStatement ps =
                    cn.prepareStatement(sql)){



            ps.setInt(1,usuarioId);



            ResultSet rs =
                    ps.executeQuery();



            while(rs.next()){


                lista.add(
                        mapearCita(rs)
                );


            }



        }catch(SQLException e){


            System.out.println(
                    "Error listando citas usuario: "
                            + e.getMessage()
            );


        }



        return lista;

    }









    @Override
    public List<Cita> listarTodas(){



        List<Cita> lista =
                new ArrayList<>();



        String sql = """
                SELECT *
                FROM citas
                ORDER BY fecha,hora
                """;



        try(Connection cn = Conexion.getConexion();

            Statement st =
                    cn.createStatement();

            ResultSet rs =
                    st.executeQuery(sql)){



            while(rs.next()){


                lista.add(
                        mapearCita(rs)
                );


            }



        }catch(SQLException e){


            System.out.println(
                    "Error listando citas: "
                            + e.getMessage()
            );

        }



        return lista;

    }









    @Override
    public List<Cita> listarPorMedico(int medicoId){



        List<Cita> lista =
                new ArrayList<>();



        String sql = """
                SELECT *
                FROM citas
                WHERE medico_id=?
                ORDER BY fecha,hora
                """;



        try(Connection cn = Conexion.getConexion();

            PreparedStatement ps =
                    cn.prepareStatement(sql)){



            ps.setInt(1,medicoId);



            ResultSet rs =
                    ps.executeQuery();



            while(rs.next()){


                lista.add(
                        mapearCita(rs)
                );


            }



        }catch(SQLException e){


            System.out.println(
                    "Error citas medico: "
                            +e.getMessage()
            );


        }



        return lista;

    }









    @Override
    public boolean actualizarEstado(
            int idCita,
            String nuevoEstado){



        String sql = """
                UPDATE citas
                SET estado=?
                WHERE id=?
                """;



        try(Connection cn = Conexion.getConexion();

            PreparedStatement ps =
                    cn.prepareStatement(sql)){



            ps.setString(
                    1,
                    nuevoEstado
            );


            ps.setInt(
                    2,
                    idCita
            );



            return ps.executeUpdate() > 0;



        }catch(SQLException e){


            System.out.println(
                    "Error actualizando cita: "
                            +e.getMessage()
            );


            return false;

        }

    }









    @Override
    public boolean eliminar(int idCita){



        String sql = """
                DELETE FROM citas
                WHERE id=?
                """;



        try(Connection cn = Conexion.getConexion();

            PreparedStatement ps =
                    cn.prepareStatement(sql)){



            ps.setInt(
                    1,
                    idCita
            );



            return ps.executeUpdate() > 0;



        }catch(SQLException e){


            System.out.println(
                    "Error eliminando cita: "
                            +e.getMessage()
            );


            return false;

        }

    }









    private Cita mapearCita(ResultSet rs)
            throws SQLException {



        return new Cita(

                rs.getInt("id"),

                rs.getInt("usuario_id"),

                rs.getInt("medico_id"),

                rs.getString("medico"),

                rs.getString("especialidad"),

                rs.getDate("fecha"),

                rs.getTime("hora"),

                rs.getString("estado")

        );


    }


}