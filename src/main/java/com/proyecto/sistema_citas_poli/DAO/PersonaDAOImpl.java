package com.proyecto.sistema_citas_poli.DAO;

import com.proyecto.sistema_citas_poli.BaseDatos.Conexion;
import com.proyecto.sistema_citas_poli.Model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PersonaDAOImpl implements PersonaDAO {


    @Override
    public boolean crear(Persona persona) {

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
                VALUES (?,?,?,?,?,?,?)
                RETURNING id
                """;

        Connection cn = null;

        try {

            cn = Conexion.getConexion();

            if (cn == null) {
                return false;
            }

            cn.setAutoCommit(false);

            int idUsuario;

            try (PreparedStatement ps = cn.prepareStatement(sqlUsuario)) {

                ps.setString(1, persona.getNombre());
                ps.setString(2, persona.getApellido());
                ps.setString(3, persona.getCorreo());
                ps.setString(4, persona.getContrasena());
                ps.setString(5, persona.getRol());
                ps.setString(6, persona.getTelefono());
                ps.setBoolean(7, persona.isActivo());

                ResultSet rs = ps.executeQuery();

                rs.next();

                idUsuario = rs.getInt("id");
            }

            // Si es paciente
            if (persona instanceof Paciente paciente) {

                String sqlPaciente = """
                        INSERT INTO paciente
                        (
                            usuario_id,
                            historial_clinico
                        )
                        VALUES (?,?)
                        """;

                try (PreparedStatement ps = cn.prepareStatement(sqlPaciente)) {

                    ps.setInt(1, idUsuario);
                    ps.setString(2, paciente.getHistorialClinico());

                    ps.executeUpdate();
                }

            }

            // Si es médico
            if (persona instanceof Medico medico) {

                String sqlMedico = """
                        INSERT INTO medico
                        (
                            usuario_id,
                            especialidad
                        )
                        VALUES (?,?)
                        """;

                try (PreparedStatement ps = cn.prepareStatement(sqlMedico)) {

                    ps.setInt(1, idUsuario);
                    ps.setString(2, medico.getEspecialidad());

                    ps.executeUpdate();
                }

            }

            cn.commit();

            return true;

        } catch (SQLException e) {

            try {
                if (cn != null) {
                    cn.rollback();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

            System.out.println("Error creando usuario: " + e.getMessage());

            return false;

        } finally {

            try {
                if (cn != null) {
                    cn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }
        @Override
        public Persona login(String correo, String contrasena) {

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
                p.historial_clinico,
                m.id AS medico_id,
                m.especialidad
            FROM usuario u
            LEFT JOIN paciente p
                ON u.id = p.usuario_id
            LEFT JOIN medico m
                ON u.id = m.usuario_id
            WHERE u.correo = ?
              AND u.contrasena = ?
            """;

            try (Connection cn = Conexion.getConexion();
                 PreparedStatement ps = cn.prepareStatement(sql)) {

                ps.setString(1, correo);
                ps.setString(2, contrasena);

                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    return convertirPersona(rs);
                }

            } catch (SQLException e) {

                System.out.println("Error login: " + e.getMessage());

            }

            return null;
        }
        @Override
        public List<Persona> buscar(String texto) {

            List<Persona> lista = new ArrayList<>();

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
                p.historial_clinico,
                m.id AS medico_id,
                m.especialidad
            FROM usuario u
            LEFT JOIN paciente p
                ON u.id = p.usuario_id
            LEFT JOIN medico m
                ON u.id = m.usuario_id
            WHERE
                LOWER(u.nombre) LIKE LOWER(?)
                OR LOWER(u.apellido) LIKE LOWER(?)
                OR LOWER(u.correo) LIKE LOWER(?)
            ORDER BY u.id
            """;

            try (Connection cn = Conexion.getConexion();
                 PreparedStatement ps = cn.prepareStatement(sql)) {

                String filtro = "%" + texto + "%";

                ps.setString(1, filtro);
                ps.setString(2, filtro);
                ps.setString(3, filtro);

                ResultSet rs = ps.executeQuery();

                while (rs.next()) {

                    lista.add(convertirPersona(rs));

                }

            } catch (SQLException e) {

                System.out.println("Error buscando usuarios: " + e.getMessage());

            }

            return lista;
        }
        @Override
        public int contarUsuarios() {

            String sql = "SELECT COUNT(*) FROM usuario";

            try (Connection cn = Conexion.getConexion();
                 Statement st = cn.createStatement();
                 ResultSet rs = st.executeQuery(sql)) {

                if (rs.next()) {
                    return rs.getInt(1);
                }

            } catch (SQLException e) {

                System.out.println("Error contando usuarios: " + e.getMessage());

            }

            return 0;
        }
        @Override
        public List<Persona> listarTodos() {

            List<Persona> lista = new ArrayList<>();

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

                p.historial_clinico,

                m.id AS medico_id,
                m.especialidad

            FROM usuario u

            LEFT JOIN paciente p
                ON u.id = p.usuario_id

            LEFT JOIN medico m
                ON u.id = m.usuario_id

            ORDER BY u.id
            """;


            try(Connection cn = Conexion.getConexion();

                Statement st = cn.createStatement();

                ResultSet rs = st.executeQuery(sql)) {


                while(rs.next()) {

                    lista.add(
                            convertirPersona(rs)
                    );

                }


            }catch(SQLException e){

                System.out.println(
                        "Error listando usuarios: "
                                + e.getMessage()
                );

            }


            return lista;

        }
        @Override
        public Persona buscarPorId(int id) {


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

                p.historial_clinico,

                m.id AS medico_id,
                m.especialidad

            FROM usuario u

            LEFT JOIN paciente p
                ON u.id = p.usuario_id

            LEFT JOIN medico m
                ON u.id = m.usuario_id

            WHERE u.id = ?
            """;


            try(Connection cn = Conexion.getConexion();

                PreparedStatement ps = cn.prepareStatement(sql)) {


                ps.setInt(1,id);


                ResultSet rs = ps.executeQuery();



                if(rs.next()) {


                    return convertirPersona(rs);


                }



            }catch(SQLException e){


                System.out.println(
                        "Error buscando usuario: "
                                + e.getMessage()
                );


            }



            return null;

        }
        @Override
        public boolean actualizar(Persona persona) {


            String sql = """
            UPDATE usuario

            SET
                nombre=?,
                apellido=?,
                correo=?,
                contrasena=?,
                rol=?,
                telefono=?,
                activo=?

            WHERE id=?
            """;


            try(Connection cn = Conexion.getConexion();

                PreparedStatement ps = cn.prepareStatement(sql)) {


                ps.setString(
                        1,
                        persona.getNombre()
                );


                ps.setString(
                        2,
                        persona.getApellido()
                );


                ps.setString(
                        3,
                        persona.getCorreo()
                );


                ps.setString(
                        4,
                        persona.getContrasena()
                );


                ps.setString(
                        5,
                        persona.getRol()
                );


                ps.setString(
                        6,
                        persona.getTelefono()
                );


                ps.setBoolean(
                        7,
                        persona.isActivo()
                );


                ps.setInt(
                        8,
                        persona.getId()
                );



                ps.executeUpdate();


                return true;



            }catch(SQLException e){


                System.out.println(
                        "Error actualizando usuario: "
                                + e.getMessage()
                );


                return false;

            }

        }
        @Override
        public boolean eliminar(int id) {


            String sql = """
            DELETE FROM usuario
            WHERE id=?
            """;


            try(Connection cn = Conexion.getConexion();

                PreparedStatement ps = cn.prepareStatement(sql)) {



                ps.setInt(
                        1,
                        id
                );


                ps.executeUpdate();


                return true;



            }catch(SQLException e){


                System.out.println(
                        "Error eliminando usuario: "
                                + e.getMessage()
                );


                return false;

            }

        }
        private Persona convertirPersona(ResultSet rs)
        throws SQLException {


            String rol = rs.getString("rol")
                    .toUpperCase();



            switch(rol){


                case "ADMINISTRADOR":


                    return new Administrador(

                            rs.getInt("id"),

                            rs.getString("nombre"),

                            rs.getString("apellido"),

                            rs.getString("correo"),

                            rs.getString("contrasena"),

                            rol,

                            rs.getString("telefono"),

                            rs.getBoolean("activo")

                    );




                case "MEDICO":


                    return new Medico(

                            rs.getInt("id"),

                            rs.getString("nombre"),

                            rs.getString("apellido"),

                            rs.getString("correo"),

                            rs.getString("contrasena"),

                            rol,

                            rs.getString("telefono"),

                            rs.getBoolean("activo"),

                            rs.getInt("medico_id"),

                            rs.getString("especialidad")

                    );





                case "PACIENTE":


                default:


                    return new Paciente(

                            rs.getInt("id"),

                            rs.getString("nombre"),

                            rs.getString("apellido"),

                            rs.getString("correo"),

                            rs.getString("contrasena"),

                            rol,

                            rs.getString("telefono"),

                            rs.getBoolean("activo"),

                            rs.getString("historial_clinico")

                    );

            }

        }

    }