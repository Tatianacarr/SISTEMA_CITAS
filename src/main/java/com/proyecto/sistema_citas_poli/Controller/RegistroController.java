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

    @FXML
    private Button btnRegistrar;



    private final PersonaDAO personaDAO =
            new PersonaDAOImpl();



    // Para edición desde administrador
    private Persona personaEditar;




    @FXML
    public void initialize(){


        cbRol.getItems().addAll(
                "PACIENTE",
                "MEDICO",
                "ADMINISTRADOR"
        );


        ocultarEspecialidad();



        cbRol.setOnAction(event -> {


            String rol =
                    cbRol.getValue();



            if("MEDICO".equals(rol)){


                txtEspecialidad.setVisible(true);
                txtEspecialidad.setManaged(true);


            }else{


                ocultarEspecialidad();


            }


        });


    }





    //====================================
    // CARGAR USUARIO PARA EDITAR
    //====================================

    public void cargarUsuario(Persona persona){


        this.personaEditar = persona;



        txtNombre.setText(
                persona.getNombre()
        );


        txtApellido.setText(
                persona.getApellido()
        );


        txtCorreo.setText(
                persona.getCorreo()
        );


        txtTelefono.setText(
                persona.getTelefono()
        );


        txtContrasena.setText(
                persona.getContrasena()
        );


        txtConfirmar.setText(
                persona.getContrasena()
        );


        cbRol.setValue(
                persona.getRol()
        );


        btnRegistrar.setText(
                "Actualizar"
        );


    }






    //====================================
    // REGISTRAR / ACTUALIZAR
    //====================================


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
                || rol == null){


            alerta(
                    "Campos incompletos",
                    "Complete los campos obligatorios"
            );


            return;

        }



        if(!correo.contains("@")){


            alerta(
                    "Correo inválido",
                    "Ingrese un correo correcto"
            );


            return;

        }




        //================================
        // ACTUALIZAR USUARIO
        //================================


        if(personaEditar != null){


            personaEditar.setNombre(nombre);

            personaEditar.setApellido(apellido);

            personaEditar.setCorreo(correo);

            personaEditar.setTelefono(telefono);

            personaEditar.setRol(rol);



            boolean actualizado =
                    personaDAO.actualizar(
                            personaEditar
                    );



            if(actualizado){


                alerta(
                        "Actualizado",
                        "Usuario actualizado correctamente"
                );


                limpiar();



            }else{


                alerta(
                        "Error",
                        "No se pudo actualizar usuario"
                );


            }



            return;

        }







        //================================
        // NUEVO REGISTRO
        //================================


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
                        txtEspecialidad
                                .getText()
                                .trim();



                if(especialidad.isEmpty()){


                    alerta(
                            "Especialidad requerida",
                            "Ingrese la especialidad médica"
                    );


                    return;

                }




                persona =
                        new Medico(
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


                persona =
                        new Administrador(
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


                persona =
                        new Paciente(
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
                    "No se pudo registrar usuario"
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


        ocultarEspecialidad();


    }







    private void ocultarEspecialidad(){


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
                                    ));



            Scene scene =
                    new Scene(
                            loader.load()
                    );



            Stage stage =
                    (Stage)
                            btnVolver
                                    .getScene()
                                    .getWindow();



            stage.setScene(scene);



        }catch(Exception e){


            e.printStackTrace();


            alerta(
                    "Error",
                    "No se pudo volver al login"
            );


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