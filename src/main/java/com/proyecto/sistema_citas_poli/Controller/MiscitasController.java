package com.proyecto.sistema_citas_poli.Controller;


import com.proyecto.sistema_citas_poli.DAO.CitaDAO;
import com.proyecto.sistema_citas_poli.DAO.CitaDAOImpl;

import com.proyecto.sistema_citas_poli.Model.Cita;
import com.proyecto.sistema_citas_poli.Util.Sesion;


import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.scene.Scene;
import javafx.scene.control.*;

import javafx.stage.Stage;

import java.sql.Date;
import java.sql.Time;



public class MiscitasController {



    @FXML
    private TableView<Cita> tablaCitas;


    @FXML
    private TableColumn<Cita,String> colMedico;


    @FXML
    private TableColumn<Cita,String> colEspecialidad;


    @FXML
    private TableColumn<Cita,Date> colFecha;


    @FXML
    private TableColumn<Cita,Time> colHora;


    @FXML
    private TableColumn<Cita,String> colEstado;



    private final CitaDAO citaDAO =
            new CitaDAOImpl();



    private ObservableList<Cita> lista =
            FXCollections.observableArrayList();
    
    @FXML
    public void initialize(){


        configurarTabla();


        cargarCitas();


    }

    private void configurarTabla(){



        colMedico.setCellValueFactory(

                c ->
                        new SimpleStringProperty(

                                c.getValue()
                                        .getMedico()

                        )

        );
        colEspecialidad.setCellValueFactory(

                c ->
                        new SimpleStringProperty(

                                c.getValue()
                                        .getEspecialidad()

                        )

        );
        colFecha.setCellValueFactory(

                c ->
                        new SimpleObjectProperty<>(

                                c.getValue()
                                        .getFecha()

                        )

        );

        colHora.setCellValueFactory(

                c ->
                        new SimpleObjectProperty<>(

                                c.getValue()
                                        .getHora()

                        )

        );

        colEstado.setCellValueFactory(

                c ->
                        new SimpleStringProperty(

                                c.getValue()
                                        .getEstado()

                        )

        );


    }

    private void cargarCitas(){



        if(!Sesion.existeSesion()){


            alerta(
                    "Error",
                    "No existe usuario activo"
            );


            return;

        }



        lista.clear();



        lista.addAll(

                citaDAO.listarPorUsuario(

                        Sesion.getPersona()
                                .getId()

                )

        );



        tablaCitas.setItems(lista);



    }
    @FXML
    public void cancelarCita(){



        Cita seleccionada =
                tablaCitas.getSelectionModel()
                        .getSelectedItem();




        if(seleccionada == null){


            alerta(
                    "Aviso",
                    "Seleccione una cita"
            );


            return;

        }


        boolean resultado =

                citaDAO.actualizarEstado(

                        seleccionada.getId(),

                        "Cancelada"

                );





        if(resultado){


            alerta(
                    "Correcto",
                    "La cita fue cancelada"
            );


            cargarCitas();



        }else{


            alerta(
                    "Error",
                    "No se pudo cancelar la cita"
            );


        }



    }

    @FXML
    public void eliminarCita(){



        Cita seleccionada =

                tablaCitas.getSelectionModel()
                        .getSelectedItem();




        if(seleccionada == null){


            alerta(
                    "Aviso",
                    "Seleccione una cita"
            );


            return;

        }





        Alert confirmacion =

                new Alert(
                        Alert.AlertType.CONFIRMATION
                );


        confirmacion.setTitle(
                "Eliminar cita"
        );


        confirmacion.setHeaderText(null);


        confirmacion.setContentText(
                "¿Está seguro de eliminar esta cita?"
        );



        if(confirmacion.showAndWait()
                .get()
                ==
                ButtonType.OK){





            boolean eliminado =

                    citaDAO.eliminar(

                            seleccionada.getId()

                    );





            if(eliminado){


                alerta(
                        "Correcto",
                        "Cita eliminada"
                );


                cargarCitas();



            }else{


                alerta(
                        "Error",
                        "No se pudo eliminar"
                );


            }


        }



    }


    @FXML
    public void actualizarTabla() {


        cargarCitas();


    }

    @FXML
    public void volver(){


        try{


            FXMLLoader loader =

                    new FXMLLoader(

                            getClass()
                                    .getResource(

                                            "/com/proyecto/sistema_citas_poli/paciente.fxml"

                                    )

                    );



            Scene scene =

                    new Scene(

                            loader.load()

                    );



            Stage stage =

                    (Stage)

                            tablaCitas.getScene()
                                    .getWindow();



            stage.setScene(scene);



            stage.setTitle(
                    "Panel Paciente"
            );



        }catch(Exception e){


            e.printStackTrace();


        }


    }


    private void alerta(
            String titulo,
            String mensaje){



        Alert alert =

                new Alert(
                        Alert.AlertType.INFORMATION
                );


        alert.setTitle(titulo);

        alert.setHeaderText(null);

        alert.setContentText(mensaje);

        alert.showAndWait();


    }



}