package com.proyecto.sistema_citas_poli.DAO;


import com.proyecto.sistema_citas_poli.BaseDatos.Conexion;
import com.proyecto.sistema_citas_poli.Model.Medico;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class MedicoDAOImpl implements MedicoDAO {


    @Override
    public boolean crear(Medico medico) {


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


        String sqlMedico = """
                INSERT INTO medico
                (
                    usuario_id,
                    especialidad
                )
                VALUES(?,?)
                """;


        try(Connection cn = Conexion.getConexion()){


            cn.setAutoCommit(false);


            int usuarioId;


            try(PreparedStatement ps =
                        cn.prepareStatement(sqlUsuario)){


                ps.setString(1, medico.getNombre());
                ps.setString(2, medico.getApellido());
                ps.setString(3, medico.getCorreo());
                ps.setString(4, medico.getContrasena());
                ps.setString(5, "MEDICO");
                ps.setString(6, medico.getTelefono());
                ps.setBoolean(7, medico.isActivo());


                ResultSet rs = ps.executeQuery();


                if(rs.next()){

                    usuarioId = rs.getInt(1);

                }else{

                    cn.rollback();
                    return false;

                }

            }



            try(PreparedStatement ps =
                        cn.prepareStatement(sqlMedico)){


                ps.setInt(1, usuarioId);
                ps.setString(2, medico.getEspecialidad());


                ps.executeUpdate();

            }


            cn.commit();


            return true;



        }catch(SQLException e){


            System.out.println(
                    "Error creando medico: "
                            + e.getMessage()
            );


            return false;

        }

    }




    @Override
    public List<Medico> leerTodos(){


        List<Medico> lista =
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

                m.id AS medico_id,
                m.especialidad

                FROM usuario u

                INNER JOIN medico m

                ON u.id = m.usuario_id

                """;



        try(Connection cn = Conexion.getConexion();

            Statement st = cn.createStatement();

            ResultSet rs = st.executeQuery(sql)){



            while(rs.next()){


                lista.add(
                        convertir(rs)
                );


            }


        }catch(SQLException e){


            System.out.println(
                    "Error listando medicos: "
                            + e.getMessage()
            );

        }



        return lista;

    }





    @Override
    public List<Medico> buscarPorEspecialidad(String especialidad){


        List<Medico> lista =
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

                m.id AS medico_id,
                m.especialidad

                FROM usuario u

                INNER JOIN medico m

                ON u.id=m.usuario_id

                WHERE LOWER(m.especialidad)
                LIKE LOWER(?)

                """;



        try(Connection cn = Conexion.getConexion();

            PreparedStatement ps =
                    cn.prepareStatement(sql)){



            ps.setString(
                    1,
                    "%" + especialidad + "%"
            );



            ResultSet rs =
                    ps.executeQuery();



            while(rs.next()){


                lista.add(
                        convertir(rs)
                );


            }



        }catch(SQLException e){


            System.out.println(
                    "Error buscando médicos: "
                            + e.getMessage()
            );

        }



        return lista;

    }







    @Override
    public Medico buscarPorId(int id){


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

                m.id AS medico_id,
                m.especialidad

                FROM usuario u

                INNER JOIN medico m

                ON u.id=m.usuario_id

                WHERE m.id=?

                """;



        try(Connection cn = Conexion.getConexion();

            PreparedStatement ps =
                    cn.prepareStatement(sql)){



            ps.setInt(1,id);



            ResultSet rs =
                    ps.executeQuery();



            if(rs.next()){

                return convertir(rs);

            }



        }catch(SQLException e){


            System.out.println(
                    "Error buscando medico: "
                            + e.getMessage()
            );


        }



        return null;

    }







    @Override
    public boolean actualizar(Medico medico){



        String sqlUsuario = """
                UPDATE usuario

                SET nombre=?,
                    apellido=?,
                    correo=?,
                    telefono=?,
                    activo=?

                WHERE id=?

                """;



        String sqlMedico = """
                UPDATE medico

                SET especialidad=?

                WHERE id=?

                """;



        try(Connection cn = Conexion.getConexion()){


            cn.setAutoCommit(false);



            try(PreparedStatement ps =
                        cn.prepareStatement(sqlUsuario)){


                ps.setString(1, medico.getNombre());
                ps.setString(2, medico.getApellido());
                ps.setString(3, medico.getCorreo());
                ps.setString(4, medico.getTelefono());
                ps.setBoolean(5, medico.isActivo());
                ps.setInt(6, medico.getId());


                ps.executeUpdate();

            }



            try(PreparedStatement ps =
                        cn.prepareStatement(sqlMedico)){


                ps.setString(
                        1,
                        medico.getEspecialidad()
                );


                ps.setInt(
                        2,
                        medico.getMedicoId()
                );


                ps.executeUpdate();

            }



            cn.commit();


            return true;



        }catch(SQLException e){


            System.out.println(
                    "Error actualizando medico: "
                            + e.getMessage()
            );


            return false;

        }

    }







    @Override
    public boolean eliminar(int id){



        String sql = """
                DELETE FROM usuario

                WHERE id IN
                (
                    SELECT usuario_id
                    FROM medico
                    WHERE id=?
                )

                """;



        try(Connection cn = Conexion.getConexion();

            PreparedStatement ps =
                    cn.prepareStatement(sql)){



            ps.setInt(1,id);


            return ps.executeUpdate()>0;



        }catch(SQLException e){


            System.out.println(
                    "Error eliminando medico: "
                            + e.getMessage()
            );


            return false;

        }

    }







    private Medico convertir(ResultSet rs)
            throws SQLException{


        return new Medico(

                rs.getInt("id"),

                rs.getString("nombre"),

                rs.getString("apellido"),

                rs.getString("correo"),

                rs.getString("contrasena"),

                rs.getString("rol"),

                rs.getString("telefono"),

                rs.getBoolean("activo"),

                rs.getInt("medico_id"),

                rs.getString("especialidad")

        );


    }


}