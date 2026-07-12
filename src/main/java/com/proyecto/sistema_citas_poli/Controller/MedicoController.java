package com.proyecto.sistema_citas_poli.Controller;


import com.proyecto.sistema_citas_poli.DAO.CitaDAO;
import com.proyecto.sistema_citas_poli.DAO.CitaDAOImpl;
import com.proyecto.sistema_citas_poli.Model.Cita;
import com.proyecto.sistema_citas_poli.Model.Medico;
import com.proyecto.sistema_citas_poli.Util.Sesion;


import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.scene.Scene;
import javafx.scene.control.*;

import javafx.stage.Stage;



public class MedicoController {



    @FXML
    private Label lblNombre;


    @FXML
    private Label lblEspecialidad;



    @FXML
    private TableView<Cita> tablaCitas;



    @FXML
    private TableColumn<Cita,String> colPaciente;


    @FXML
    private TableColumn<Cita,String> colEspecialidad;


    @FXML
    private TableColumn<Cita,String> colFecha;


    @FXML
    private TableColumn<Cita,String> colHora;


    @FXML
    private TableColumn<Cita,String> colEstado;



    @FXML
    private TableColumn<Cita,String> colObservacion;




    private final CitaDAO citaDAO =
            new CitaDAOImpl();



    private final ObservableList<Cita> lista =
            FXCollections.observableArrayList();







    @FXML
    public void initialize(){


        cargarDatosMedico();

        configurarTabla();

        cargarCitas();

    }









    private void cargarDatosMedico(){


        if(!Sesion.existeSesion())
            return;



        Medico medico =
                (Medico) Sesion.getPersona();



        lblNombre.setText(
                medico.getNombre()
                        +" "
                        +medico.getApellido()
        );



        lblEspecialidad.setText(
                medico.getEspecialidad()
        );


    }









    private void configurarTabla(){



        // NOMBRE DEL PACIENTE

        colPaciente.setCellValueFactory(

                c -> new SimpleStringProperty(

                        c.getValue()
                                .getPaciente()

                )

        );




        colEspecialidad.setCellValueFactory(

                c -> new SimpleStringProperty(

                        c.getValue()
                                .getEspecialidad()

                )

        );




        colFecha.setCellValueFactory(

                c -> new SimpleStringProperty(

                        c.getValue()
                                .getFecha()
                                .toString()

                )

        );




        colHora.setCellValueFactory(

                c -> new SimpleStringProperty(

                        c.getValue()
                                .getHora()
                                .toString()

                )

        );




        colEstado.setCellValueFactory(

                c -> new SimpleStringProperty(

                        c.getValue()
                                .getEstado()

                )

        );





        colObservacion.setCellValueFactory(

                c -> new SimpleStringProperty(

                        c.getValue()
                                .getObservacion()

                )

        );



        tablaCitas.setItems(lista);


    }












    private void cargarCitas(){



        if(!Sesion.existeSesion())
            return;



        Medico medico =
                (Medico) Sesion.getPersona();




        lista.clear();




        lista.addAll(

                citaDAO.listarPorMedico(

                        medico.getMedicoId()

                )

        );



        tablaCitas.refresh();


    }












    @FXML
    public void atenderCita(){



        Cita cita =

                tablaCitas
                        .getSelectionModel()
                        .getSelectedItem();




        if(cita == null){


            alerta(
                    "Aviso",
                    "Seleccione una cita"
            );


            return;

        }





        TextInputDialog dialog =

                new TextInputDialog();



        dialog.setTitle(
                "Atender cita"
        );


        dialog.setHeaderText(
                "Observación médica"
        );


        dialog.setContentText(
                "Ingrese observación:"
        );





        dialog.showAndWait()

                .ifPresent(observacion -> {



                    cita.setObservacion(
                            observacion
                    );



                    boolean actualizado =

                            citaDAO.actualizar(

                                    cita

                            );





                    if(actualizado){


                        citaDAO.actualizarEstado(

                                cita.getId(),

                                "Atendida"

                        );



                        alerta(
                                "Correcto",
                                "Cita atendida correctamente"
                        );



                        cargarCitas();


                    }



                });



    }












    @FXML
    public void cancelarCita(){



        Cita cita =

                tablaCitas
                        .getSelectionModel()
                        .getSelectedItem();




        if(cita == null){


            alerta(
                    "Aviso",
                    "Seleccione una cita"
            );


            return;

        }





        if(citaDAO.actualizarEstado(

                cita.getId(),

                "Cancelada"

        )){



            alerta(
                    "Correcto",
                    "Cita cancelada"
            );



            cargarCitas();



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

                            tablaCitas
                                    .getScene()
                                    .getWindow();



            stage.setScene(scene);



            stage.setTitle(
                    "Inicio de sesión"
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