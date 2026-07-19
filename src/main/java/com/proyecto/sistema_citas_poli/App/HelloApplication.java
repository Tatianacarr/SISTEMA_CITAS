package com.proyecto.sistema_citas_poli.App;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class HelloApplication extends Application {


    @Override
    public void start(Stage stage) throws Exception {


        FXMLLoader loader =
                new FXMLLoader(
                        HelloApplication.class.getResource("/com/proyecto/sistema_citas_poli/login.fxml")
                );


        Scene scene = new Scene(loader.load());


        stage.setTitle("Sistema de Citas Médicas POLI");

        stage.setScene(scene);

        stage.show();

    }



    public static void main(String[] args){

        launch();

    }

}