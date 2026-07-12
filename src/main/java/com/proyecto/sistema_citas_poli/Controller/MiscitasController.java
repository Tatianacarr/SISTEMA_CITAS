package com.proyecto.sistema_citas_poli.Controller;


import com.proyecto.sistema_citas_poli.DAO.CitaDAO;
import com.proyecto.sistema_citas_poli.DAO.CitaDAOImpl;
import com.proyecto.sistema_citas_poli.Model.Cita;
import com.proyecto.sistema_citas_poli.Model.Persona;
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
    private Label lblPaciente;



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



    private final ObservableList<Cita> lista =
            FXCollections.observableArrayList();





    @FXML
    public void initialize(){


        cargarPaciente();


        configurarTabla();


        cargarCitas();


    }





    private void cargarPaciente(){


        if(Sesion.existeSesion()){


            Persona paciente =
                    Sesion.getPersona();



            if(lblPaciente != null){

                lblPaciente.setText(
                        paciente.getNombre()
                                +" "
                                +paciente.getApellido()
                );

            }


        }


    }





    private void configurarTabla(){



        colMedico.setCellValueFactory(

                c -> new SimpleStringProperty(

                        c.getValue()
                                .getMedico()

                )

        );



        colEspecialidad.setCellValueFactory(

                c -> new SimpleStringProperty(

                        c.getValue()
                                .getEspecialidad()

                )

        );



        colFecha.setCellValueFactory(

                c -> new SimpleObjectProperty<>(

                        c.getValue()
                                .getFecha()

                )

        );



        colHora.setCellValueFactory(

                c -> new SimpleObjectProperty<>(

                        c.getValue()
                                .getHora()

                )

        );



        colEstado.setCellValueFactory(

                c -> new SimpleStringProperty(

                        c.getValue()
                                .getEstado()

                )

        );


    }








    private void cargarCitas(){



        if(!Sesion.existeSesion()){


            alerta(
                    "Error",
                    "No existe sesión activa"
            );


            return;

        }




        int usuarioId =

                Sesion.getPersona()
                        .getId();



        lista.clear();



        // SOLO LAS CITAS DEL PACIENTE LOGUEADO

        lista.addAll(

                citaDAO.listarPorUsuario(
                        usuarioId
                )

        );



        tablaCitas.setItems(lista);



        System.out.println(
                "Citas del usuario "
                        +usuarioId
                        +" : "
                        +lista.size()
        );



    }










    @FXML
    public void cancelarCita(){



        Cita cita =

                tablaCitas.getSelectionModel()
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
    public void eliminarCita(){



        Cita cita =

                tablaCitas.getSelectionModel()
                        .getSelectedItem();




        if(cita == null){


            alerta(
                    "Aviso",
                    "Seleccione una cita"
            );


            return;

        }





        Alert confirmar =

                new Alert(
                        Alert.AlertType.CONFIRMATION
                );



        confirmar.setContentText(
                "¿Eliminar esta cita?"
        );



        if(confirmar.showAndWait()
                .get()
                ==
                ButtonType.OK){



            if(citaDAO.eliminar(cita.getId())){


                alerta(
                        "Correcto",
                        "Cita eliminada"
                );


                cargarCitas();

            }


        }



    }









    @FXML
    public void editarCita(){


        Cita cita =
                tablaCitas.getSelectionModel()
                        .getSelectedItem();



        if(cita == null){


            alerta(
                    "Aviso",
                    "Seleccione una cita para editar"
            );

            return;

        }



        Dialog<ButtonType> dialog =
                new Dialog<>();


        dialog.setTitle("Editar cita");

        dialog.setHeaderText(
                "Modificar fecha y hora"
        );



        DatePicker fechaNueva =
                new DatePicker(
                        cita.getFecha()
                                .toLocalDate()
                );



        ComboBox<String> horaNueva =
                new ComboBox<>();


        horaNueva.setItems(

                FXCollections.observableArrayList(

                        "08:00",
                        "09:00",
                        "10:00",
                        "11:00",
                        "14:00",
                        "15:00",
                        "16:00"

                )

        );



        horaNueva.setValue(

                cita.getHora()
                        .toString()
                        .substring(0,5)

        );



        javafx.scene.layout.VBox contenido =
                new javafx.scene.layout.VBox(
                        15,
                        new Label("Nueva fecha"),
                        fechaNueva,
                        new Label("Nueva hora"),
                        horaNueva
                );


        contenido.setPadding(
                new javafx.geometry.Insets(20)
        );



        dialog.getDialogPane()
                .setContent(contenido);



        dialog.getDialogPane()
                .getButtonTypes()
                .addAll(

                        ButtonType.OK,
                        ButtonType.CANCEL

                );





        if(dialog.showAndWait()
                .get()
                ==
                ButtonType.OK){



            if(fechaNueva.getValue()==null
                    ||
                    horaNueva.getValue()==null){


                alerta(
                        "Error",
                        "Complete todos los campos"
                );

                return;

            }





            cita.setFecha(

                    Date.valueOf(
                            fechaNueva.getValue()
                    )

            );



            cita.setHora(

                    Time.valueOf(
                            horaNueva.getValue()
                                    +":00"
                    )

            );





            if(citaDAO.actualizar(cita)){



                alerta(
                        "Correcto",
                        "Cita actualizada correctamente"
                );



                cargarCitas();



            }else{


                alerta(
                        "Error",
                        "No se pudo actualizar la cita"
                );


            }



        }



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