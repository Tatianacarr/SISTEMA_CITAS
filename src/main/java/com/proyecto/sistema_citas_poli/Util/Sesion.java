package com.proyecto.sistema_citas_poli.Util;

import com.proyecto.sistema_citas_poli.Model.Persona;

public class Sesion {

    private static Persona personaActual;


    public static void iniciarSesion(Persona persona){

        personaActual = persona;

    }


    public static Persona getPersona(){

        return personaActual;

    }


    public static void cerrarSesion(){

        personaActual = null;

    }


    public static boolean existeSesion(){

        return personaActual != null;

    }

}