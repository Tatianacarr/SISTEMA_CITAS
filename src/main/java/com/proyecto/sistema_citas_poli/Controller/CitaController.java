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



    private final MedicoDAO medicoDAO =
            new MedicoDAOImpl();



    private final CitaDAO citaDAO =
            new CitaDAOImpl();





    @FXML
    public void initialize(){


        cargarMedicos();


        cargarHoras();



        cbMedico.setOnAction(e -> {


            Medico medico =
                    cbMedico.getValue();



            if(medico != null){


                cbEspecialidad.setValue(
                        medico.getEspecialidad()
                );


            }


        });


    }







    private void cargarMedicos(){


        cbMedico.setItems(

                FXCollections.observableArrayList(

                        medicoDAO.leerTodos()

                )

        );


    }






    private void cargarHoras(){


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


    }





    @FXML
    public void guardarCita(){


        if(cbMedico.getValue()==null){

            alerta("Error","Seleccione un médico");
            return;

        }


        if(fecha.getValue()==null){

            alerta("Error","Seleccione una fecha");
            return;

        }


        if(cbHora.getValue()==null){

            alerta("Error","Seleccione una hora");
            return;

        }



        Persona paciente = Sesion.getPersona();


        if(paciente==null){

            alerta(
                    "Error",
                    "No existe sesión activa"
            );

            return;

        }



        Medico medico = cbMedico.getValue();



        Cita cita = new Cita(

                0,

                paciente.getId(),       // usuario_id

                medico.getMedicoId(),  // medico_id


                paciente.getNombre()
                        +" "
                        +paciente.getApellido(), // PACIENTE


                medico.getNombre()
                        +" "
                        +medico.getApellido(),  // MEDICO


                medico.getEspecialidad(), // ESPECIALIDAD


                Date.valueOf(
                        fecha.getValue()
                ),


                Time.valueOf(
                        cbHora.getValue()+":00"
                ),


                "Pendiente",


                txtDescripcion.getText()

        );




        if(citaDAO.agendar(cita)){


            alerta(
                    "Correcto",
                    "Cita registrada correctamente"
            );


            limpiar();


        }else{


            alerta(
                    "Error",
                    "No se pudo guardar la cita"
            );

        }



    }


    private void limpiar(){


        cbMedico.setValue(null);

        cbEspecialidad.setValue(null);

        fecha.setValue(null);

        cbHora.setValue(null);

        txtDescripcion.clear();


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