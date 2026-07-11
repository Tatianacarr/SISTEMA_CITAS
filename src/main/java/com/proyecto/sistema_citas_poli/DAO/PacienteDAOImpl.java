package com.proyecto.sistema_citas_poli.DAO;


import com.proyecto.sistema_citas_poli.BaseDatos.Conexion;
import com.proyecto.sistema_citas_poli.Model.Paciente;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;



public class PacienteDAOImpl implements PacienteDAO {



    @Override
    public boolean crear(Paciente paciente) {


        String sqlUsuario = """
                INSERT INTO usuario
                (
                nombre,
                apellido,
                correo,
                contrasena,
                rol,
                telefono,
                activo
                )
                VALUES(?,?,?,?,?,?,?)
                RETURNING id
                """;


        String sqlPaciente = """
                INSERT INTO paciente
                (
                usuario_id,
                historial_clinico
                )
                VALUES(?,?)
                """;



        try(Connection cn = Conexion.getConexion()){


            cn.setAutoCommit(false);


            int idUsuario;



            try(PreparedStatement ps =
                        cn.prepareStatement(sqlUsuario)){



                ps.setString(1,paciente.getNombre());
                ps.setString(2,paciente.getApellido());
                ps.setString(3,paciente.getCorreo());
                ps.setString(4,paciente.getContrasena());
                ps.setString(5,"PACIENTE");
                ps.setString(6,paciente.getTelefono());
                ps.setBoolean(7,paciente.isActivo());



                ResultSet rs = ps.executeQuery();



                if(rs.next()){

                    idUsuario = rs.getInt(1);

                }else{

                    cn.rollback();
                    return false;

                }

            }



            try(PreparedStatement ps =
                        cn.prepareStatement(sqlPaciente)){


                ps.setInt(1,idUsuario);
                ps.setString(2,paciente.getHistorialClinico());


                ps.executeUpdate();

            }



            cn.commit();


            return true;



        }catch(SQLException e){


            System.out.println(
                    "Error creando paciente: "
                            +e.getMessage()
            );


            return false;

        }


    }





    @Override
    public List<Paciente> listarTodos(){


        List<Paciente> lista =
                new ArrayList<>();



        String sql = """
                SELECT
                u.id,
                u.nombre,
                u.apellido,
                u.correo,
                u.contrasena,
                u.rol,
                u.telefono,
                u.activo,
                p.historial_clinico

                FROM usuario u

                INNER JOIN paciente p

                ON u.id=p.usuario_id
                """;



        try(Connection cn=Conexion.getConexion();

            Statement st=cn.createStatement();

            ResultSet rs=st.executeQuery(sql)){



            while(rs.next()){



                lista.add(new Paciente(

                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("correo"),
                        rs.getString("contrasena"),
                        rs.getString("rol"),
                        rs.getString("telefono"),
                        rs.getBoolean("activo"),
                        rs.getString("historial_clinico")

                ));

            }



        }catch(SQLException e){


            System.out.println(
                    "Error listando pacientes: "
                            +e.getMessage()
            );

        }



        return lista;

    }







    @Override
    public Paciente buscarPorId(int id){



        String sql = """
                SELECT
                u.id,
                u.nombre,
                u.apellido,
                u.correo,
                u.contrasena,
                u.rol,
                u.telefono,
                u.activo,
                p.historial_clinico

                FROM usuario u

                INNER JOIN paciente p

                ON u.id=p.usuario_id

                WHERE u.id=?
                """;



        try(Connection cn=Conexion.getConexion();

            PreparedStatement ps=
                    cn.prepareStatement(sql)){



            ps.setInt(1,id);



            ResultSet rs=
                    ps.executeQuery();



            if(rs.next()){



                return new Paciente(

                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("correo"),
                        rs.getString("contrasena"),
                        rs.getString("rol"),
                        rs.getString("telefono"),
                        rs.getBoolean("activo"),
                        rs.getString("historial_clinico")

                );

            }



        }catch(SQLException e){


            System.out.println(
                    "Error buscando paciente: "
                            +e.getMessage()
            );

        }



        return null;

    }








    @Override
    public boolean actualizar(Paciente paciente){



        String sqlUsuario="""
                UPDATE usuario
                SET nombre=?,
                    apellido=?,
                    correo=?,
                    contrasena=?,
                    telefono=?,
                    activo=?
                WHERE id=?
                """;



        String sqlPaciente="""
                UPDATE paciente
                SET historial_clinico=?
                WHERE usuario_id=?
                """;




        try(Connection cn=Conexion.getConexion()){


            cn.setAutoCommit(false);



            try(PreparedStatement ps=
                        cn.prepareStatement(sqlUsuario)){



                ps.setString(1,paciente.getNombre());
                ps.setString(2,paciente.getApellido());
                ps.setString(3,paciente.getCorreo());
                ps.setString(4,paciente.getContrasena());
                ps.setString(5,paciente.getTelefono());
                ps.setBoolean(6,paciente.isActivo());
                ps.setInt(7,paciente.getId());


                ps.executeUpdate();

            }




            try(PreparedStatement ps=
                        cn.prepareStatement(sqlPaciente)){



                ps.setString(1,paciente.getHistorialClinico());
                ps.setInt(2,paciente.getId());


                ps.executeUpdate();

            }



            cn.commit();


            return true;



        }catch(SQLException e){


            System.out.println(
                    "Error actualizando paciente: "
                            +e.getMessage()
            );


            return false;

        }


    }







    @Override
    public boolean eliminar(int id){



        String sql=
                """
                DELETE FROM usuario
                WHERE id=?
                """;



        try(Connection cn=Conexion.getConexion();

            PreparedStatement ps=
                    cn.prepareStatement(sql)){



            ps.setInt(1,id);



            ps.executeUpdate();



            return true;



        }catch(SQLException e){



            System.out.println(
                    "Error eliminando paciente: "
                            +e.getMessage()
            );


            return false;

        }

    }



}