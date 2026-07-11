package com.proyecto.sistema_citas_poli.Controller;


import com.proyecto.sistema_citas_poli.Model.Persona;
import com.proyecto.sistema_citas_poli.Util.Sesion;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;



public class PacienteController {



    @FXML
    private Label lblBienvenida;

    @FXML
    public void initialize(){


        Persona usuario =
                Sesion.getPersona();



        if(usuario != null){


            lblBienvenida.setText(

                    "Bienvenido "
                            +
                            usuario.getNombre()

            );


        }


    }
    @FXML
    public void abrirCitas(){


        try{


            FXMLLoader loader =
                    new FXMLLoader(

                            getClass()
                                    .getResource(
                                            "/com/proyecto/sistema_citas_poli/cita.fxml"
                                    )

                    );
            Scene scene =
                    new Scene(
                            loader.load()
                    );
            Stage stage =
                    (Stage)
                            lblBienvenida
                                    .getScene()
                                    .getWindow();

            stage.setScene(scene);

            stage.setTitle(
                    "Agendar cita"
            );

        }catch(Exception e){


            System.out.println(
                    "Error abriendo citas: "
                            +
                            e.getMessage()
            );
        }
    }
    @FXML
    public void misCitas(){


        try{


            FXMLLoader loader =
                    new FXMLLoader(

                            getClass()
                                    .getResource(
                                            "/com/proyecto/sistema_citas_poli/mis_citas.fxml"
                                    )

                    );



            Scene scene =
                    new Scene(
                            loader.load()
                    );



            Stage stage =
                    (Stage)
                            lblBienvenida
                                    .getScene()
                                    .getWindow();



            stage.setScene(scene);


            stage.setTitle(
                    "Mis citas"
            );



        }catch(Exception e){


            e.printStackTrace();


        }


    }
    @FXML
    public void cerrarSesion(){
        Sesion.cerrarSesion();
        try{
            FXMLLoader loader =
                    new FXMLLoader(
                            getClass()
                                    .getResource(
                                            "/com/proyecto/sistema_citas_poli/login.fxml"
                                    )

                    );
            Scene scene =
                    new Scene(
                            loader.load()
                    );
            Stage stage =
                    (Stage)
                            lblBienvenida
                                    .getScene()
                                    .getWindow();
            stage.setScene(scene);
        }catch(Exception e) {
            System.out.println(
                    e.getMessage()
            );
        }
    }


}