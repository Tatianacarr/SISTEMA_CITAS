package com.proyecto.sistema_citas_poli.Controller;


import com.proyecto.sistema_citas_poli.DAO.PersonaDAO;
import com.proyecto.sistema_citas_poli.DAO.PersonaDAOImpl;
import com.proyecto.sistema_citas_poli.Model.*;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;



public class RegistroController {


    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtApellido;

    @FXML
    private TextField txtCorreo;

    @FXML
    private TextField txtTelefono;

    @FXML
    private PasswordField txtContrasena;

    @FXML
    private PasswordField txtConfirmar;

    @FXML
    private ComboBox<String> cbRol;

    @FXML
    private TextField txtEspecialidad;

    @FXML
    private Button btnVolver;



    private final PersonaDAO personaDAO =
            new PersonaDAOImpl();



    @FXML
    public void initialize(){


        cbRol.getItems().addAll(
                "PACIENTE",
                "MEDICO",
                "ADMINISTRADOR"
        );


        // Ocultar especialidad al iniciar
        txtEspecialidad.setVisible(false);
        txtEspecialidad.setManaged(false);



        cbRol.setOnAction(event -> {


            String rol = cbRol.getValue();



            if(rol == null){
                return;
            }



            if(rol.equals("MEDICO")){


                txtEspecialidad.setVisible(true);
                txtEspecialidad.setManaged(true);
                txtEspecialidad.setDisable(false);



            }else{


                txtEspecialidad.clear();

                txtEspecialidad.setVisible(false);
                txtEspecialidad.setManaged(false);
                txtEspecialidad.setDisable(true);


            }



        });



    }





    @FXML
    public void registrar(){



        String nombre =
                txtNombre.getText().trim();


        String apellido =
                txtApellido.getText().trim();


        String correo =
                txtCorreo.getText().trim();


        String telefono =
                txtTelefono.getText().trim();


        String password =
                txtContrasena.getText();


        String confirmar =
                txtConfirmar.getText();


        String rol =
                cbRol.getValue();



        if(nombre.isEmpty()
                || apellido.isEmpty()
                || correo.isEmpty()
                || password.isEmpty()
                || rol == null){


            alerta(
                    "Campos incompletos",
                    "Complete todos los campos obligatorios"
            );

            return;

        }



        if(!correo.contains("@")){


            alerta(
                    "Correo inválido",
                    "Ingrese un correo válido"
            );

            return;

        }



        if(password.length() < 8){


            alerta(
                    "Contraseña insegura",
                    "Debe tener mínimo 8 caracteres"
            );

            return;

        }



        if(!password.equals(confirmar)){


            alerta(
                    "Error",
                    "Las contraseñas no coinciden"
            );

            return;

        }




        Persona persona;



        switch(rol){



            case "MEDICO":


                String especialidad =
                        txtEspecialidad.getText().trim();



                if(especialidad.isEmpty()){


                    alerta(
                            "Especialidad requerida",
                            "Ingrese la especialidad médica"
                    );


                    return;

                }



                persona = new Medico(

                        0,
                        nombre,
                        apellido,
                        correo,
                        password,
                        rol,
                        telefono,
                        true,
                        0,
                        especialidad

                );


                break;





            case "ADMINISTRADOR":


                persona = new Administrador(

                        0,
                        nombre,
                        apellido,
                        correo,
                        password,
                        rol,
                        telefono,
                        true

                );


                break;





            default:



                persona = new Paciente(

                        0,
                        nombre,
                        apellido,
                        correo,
                        password,
                        rol,
                        telefono,
                        true,
                        "Sin historial"

                );


                break;


        }




        boolean guardado =
                personaDAO.crear(persona);



        if(guardado){


            alerta(
                    "Registro exitoso",
                    "Usuario creado correctamente"
            );


            limpiar();



        }else{


            alerta(
                    "Error",
                    "No se pudo crear el usuario"
            );


        }


    }







    private void limpiar(){


        txtNombre.clear();

        txtApellido.clear();

        txtCorreo.clear();

        txtTelefono.clear();

        txtContrasena.clear();

        txtConfirmar.clear();


        cbRol.getSelectionModel()
                .clearSelection();



        txtEspecialidad.clear();

        txtEspecialidad.setVisible(false);

        txtEspecialidad.setManaged(false);



    }








    @FXML
    public void volverLogin(){


        try{


            FXMLLoader loader =
                    new FXMLLoader(
                            getClass()
                                    .getResource(
                                            "/com/proyecto/sistema_citas_poli/login.fxml"
                                    )
                    );



            Scene scene =
                    new Scene(loader.load());



            Stage stage =
                    (Stage)
                            btnVolver.getScene()
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