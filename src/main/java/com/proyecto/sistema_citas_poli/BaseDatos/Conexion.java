package com.proyecto.sistema_citas_poli.BaseDatos;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;



public class Conexion {


    private static final String URL =
            "jdbc:postgresql://localhost:5432/Proyecto_sistema";


    private static final String USER =
            "postgres";


    private static final String PASSWORD =
            "123456";




    public static Connection getConexion(){


        Connection cn = null;


        try{


            Class.forName(
                    "org.postgresql.Driver"
            );



            cn = DriverManager.getConnection(
                    URL,
                    USER,
                    PASSWORD
            );



            System.out.println(
                    "Conexion exitosa"
            );



        }catch(ClassNotFoundException e){


            System.out.println(
                    "No se encontro el driver PostgreSQL"
            );


        }catch(SQLException e){


            System.out.println(
                    "Error conexion: "
                            +e.getMessage()
            );


        }



        return cn;


    }



}