package com.proyecto.sistema_citas_poli.BaseDatos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

    private static final String URL =
            "jdbc:postgresql://aws-0-ca-central-1.pooler.supabase.com:5432/postgres";

    private static final String USER =
            "postgres.btqwqnqjrkrkwjadyurx";

    private static final String PASSWORD =
            "Politecnica26";

    public static Connection getConexion() {

        Connection cn = null;

        try {

            Class.forName(
                    "org.postgresql.Driver"
            );

            cn = DriverManager.getConnection(
                    URL,
                    USER,
                    PASSWORD
            );

            System.out.println(
                    "Conexion exitosa con Supabase"
            );

        } catch (ClassNotFoundException e) {

            System.out.println(
                    "No se encontro el driver PostgreSQL"
            );

            e.printStackTrace();

        } catch (SQLException e) {

            System.out.println(
                    "Error de conexion:"
            );

            e.printStackTrace();
        }

        return cn;
    }
}