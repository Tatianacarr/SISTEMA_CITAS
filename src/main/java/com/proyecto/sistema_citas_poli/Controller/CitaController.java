package com.proyecto.sistema_citas_poli.Controller;


import com.proyecto.sistema_citas_poli.DAO.CitaDAO;
import com.proyecto.sistema_citas_poli.DAO.CitaDAOImpl;
import com.proyecto.sistema_citas_poli.DAO.MedicoDAO;
import com.proyecto.sistema_citas_poli.DAO.MedicoDAOImpl;

import com.proyecto.sistema_citas_poli.Model.Cita;
import com.proyecto.sistema_citas_poli.Model.Medico;
import com.proyecto.sistema_citas_poli.Model.Persona;

import com.proyecto.sistema_citas_poli.Util.Sesion;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.scene.Scene;
import javafx.scene.control.*;

import javafx.stage.Stage;

import java.sql.Date;
import java.sql.Time;



public class CitaController {


    @FXML
    private ComboBox<String> cbEspecialidad;


    @FXML
    private ComboBox<Medico> cbMedico;


    @FXML
    private DatePicker fecha;


    @FXML
    private ComboBox<String> cbHora;


    @FXML
    private TextArea txtDescripcion;



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



    private final MedicoDAO medicoDAO =
            new MedicoDAOImpl();


    private final CitaDAO citaDAO =
            new CitaDAOImpl();



    private ObservableList<Cita> lista =
            FXCollections.observableArrayList();




    @FXML
    public void initialize(){


        // Cargar todos los médicos
        cargarTodosLosMedicos();



        cbHora.setItems(

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



        // Cuando selecciona médico muestra especialidad
        cbMedico.setOnAction(e -> {


            Medico medico =
                    cbMedico.getValue();


            if(medico != null){


                cbEspecialidad.setValue(
                        medico.getEspecialidad()
                );


            }


        });



        configurarTabla();


        cargarCitas();


    }







    private void cargarTodosLosMedicos(){


        var medicos =
                medicoDAO.leerTodos();



        cbMedico.setItems(

                FXCollections.observableArrayList(
                        medicos
                )

        );



        System.out.println(
                "Médicos encontrados: "
                        + medicos.size()
        );


    }







    @FXML
    public void guardarCita(){



        if(cbMedico.getValue()==null){


            alerta(
                    "Error",
                    "Seleccione un médico"
            );

            return;

        }



        if(fecha.getValue()==null){


            alerta(
                    "Error",
                    "Seleccione una fecha"
            );

            return;

        }



        if(cbHora.getValue()==null){


            alerta(
                    "Error",
                    "Seleccione una hora"
            );

            return;

        }



        Persona usuario =
                Sesion.getPersona();



        if(usuario==null){


            alerta(
                    "Error",
                    "No existe sesión activa"
            );


            return;

        }




        Medico medico =
                cbMedico.getValue();





        Cita cita =
                new Cita(

                        0,

                        usuario.getId(),

                        medico.getMedicoId(),

                        medico.getNombre()
                                +" "
                                +
                                medico.getApellido(),

                        medico.getEspecialidad(),


                        Date.valueOf(
                                fecha.getValue()
                        ),


                        Time.valueOf(
                                cbHora.getValue()
                                        +":00"
                        ),


                        "Pendiente"

                );






        if(citaDAO.agendar(cita)){


            alerta(
                    "Correcto",
                    "Cita agendada correctamente"
            );


            cargarCitas();



        }else{


            alerta(
                    "Error",
                    "No se pudo guardar la cita"
            );


        }



    }







    private void cargarCitas(){


        if(!Sesion.existeSesion())
            return;



        lista.clear();



        lista.addAll(

                citaDAO.listarPorUsuario(
                        Sesion.getPersona()
                                .getId()
                )

        );



        if(tablaCitas!=null){

            tablaCitas.setItems(lista);

        }


    }







    private void configurarTabla(){


        if(tablaCitas==null)
            return;



        colMedico.setCellValueFactory(

                c -> new javafx.beans.property.SimpleStringProperty(

                        c.getValue().getMedico()

                )

        );



        colEspecialidad.setCellValueFactory(

                c -> new javafx.beans.property.SimpleStringProperty(

                        c.getValue().getEspecialidad()

                )

        );



        colFecha.setCellValueFactory(

                c -> new javafx.beans.property.SimpleObjectProperty<>(

                        c.getValue().getFecha()

                )

        );



        colHora.setCellValueFactory(

                c -> new javafx.beans.property.SimpleObjectProperty<>(

                        c.getValue().getHora()

                )

        );



        colEstado.setCellValueFactory(

                c -> new javafx.beans.property.SimpleStringProperty(

                        c.getValue().getEstado()

                )

        );


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
                    new Scene(loader.load());



            Stage stage =
                    (Stage)
                            cbMedico.getScene()
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