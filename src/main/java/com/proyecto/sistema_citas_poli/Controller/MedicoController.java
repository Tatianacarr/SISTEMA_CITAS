package com.proyecto.sistema_citas_poli.Controller;


import com.proyecto.sistema_citas_poli.DAO.CitaDAO;
import com.proyecto.sistema_citas_poli.DAO.CitaDAOImpl;
import com.proyecto.sistema_citas_poli.Model.Cita;
import com.proyecto.sistema_citas_poli.Model.Medico;
import com.proyecto.sistema_citas_poli.Util.Sesion;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.fxml.FXML;

import javafx.scene.control.*;




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
    private Button btnAtender;



    @FXML
    private Button btnCancelar;



    private final CitaDAO citaDAO =
            new CitaDAOImpl();



    private ObservableList<Cita> lista =
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
                (Medico)
                        Sesion.getPersona();



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



        colPaciente.setCellValueFactory(

                c ->
                        new javafx.beans.property.SimpleStringProperty(

                                c.getValue()
                                        .getMedico()

                        )

        );



        colEspecialidad.setCellValueFactory(

                c ->
                        new javafx.beans.property.SimpleStringProperty(

                                c.getValue()
                                        .getEspecialidad()

                        )

        );



        colFecha.setCellValueFactory(

                c ->
                        new javafx.beans.property.SimpleStringProperty(

                                c.getValue()
                                        .getFecha()
                                        .toString()

                        )

        );



        colHora.setCellValueFactory(

                c ->
                        new javafx.beans.property.SimpleStringProperty(

                                c.getValue()
                                        .getHora()
                                        .toString()

                        )

        );



        colEstado.setCellValueFactory(

                c ->
                        new javafx.beans.property.SimpleStringProperty(

                                c.getValue()
                                        .getEstado()

                        )

        );



        tablaCitas.setItems(lista);


    }









    private void cargarCitas(){



        Medico medico =
                (Medico)
                        Sesion.getPersona();



        lista.clear();



        lista.addAll(

                citaDAO.listarPorMedico(

                        medico.getMedicoId()

                )

        );

    }









    @FXML
    public void atenderCita(){



        Cita cita =
                tablaCitas.getSelectionModel()
                        .getSelectedItem();



        if(cita==null){


            alerta(
                    "Seleccione",
                    "Seleccione una cita"
            );


            return;

        }




        citaDAO.actualizarEstado(

                cita.getId(),

                "Atendida"

        );



        cargarCitas();


    }









    @FXML
    public void cancelarCita(){



        Cita cita =
                tablaCitas.getSelectionModel()
                        .getSelectedItem();



        if(cita==null)
            return;



        citaDAO.actualizarEstado(

                cita.getId(),

                "Cancelada"

        );



        cargarCitas();


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