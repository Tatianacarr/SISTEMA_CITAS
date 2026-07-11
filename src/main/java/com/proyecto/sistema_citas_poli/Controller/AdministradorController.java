package com.proyecto.sistema_citas_poli.Controller;


import com.proyecto.sistema_citas_poli.DAO.PersonaDAO;
import com.proyecto.sistema_citas_poli.DAO.PersonaDAOImpl;

import com.proyecto.sistema_citas_poli.Model.Persona;
import com.proyecto.sistema_citas_poli.Model.Administrador;
import com.proyecto.sistema_citas_poli.Model.Medico;
import com.proyecto.sistema_citas_poli.Model.Paciente;

import com.proyecto.sistema_citas_poli.Util.Sesion;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.fxml.FXML;

import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.scene.layout.VBox;

import javafx.stage.Stage;

import java.util.Optional;



public class AdministradorController {



    // ============================
    // COMPONENTES FXML
    // ============================


    @FXML
    private Label lblAdministrador;


    @FXML
    private Label lblTitulo;



    @FXML
    private TextField txtBuscar;



    @FXML
    private TableView<Object> tablaDatos;



    @FXML
    private TableColumn<Object,Object> col1;


    @FXML
    private TableColumn<Object,Object> col2;


    @FXML
    private TableColumn<Object,Object> col3;


    @FXML
    private TableColumn<Object,Object> col4;



    @FXML
    private Button btnCerrarSesion;




    // ============================
    // DATOS
    // ============================


    private ObservableList<Object> datos =
            FXCollections.observableArrayList();



    private String moduloActual="Inicio";


    private PersonaDAO personaDAO =
            new PersonaDAOImpl();





    // ============================
    // INICIALIZAR
    // ============================


    @FXML
    public void initialize(){


        configurarTabla();



        if(Sesion.getPersona()!=null){


            lblAdministrador.setText(

                    "Administrador: "
                            +
                            Sesion.getPersona().getNombre()

            );


        }



        mostrarInicio();


    }






    private void configurarTabla(){


        col1.setCellValueFactory(
                new PropertyValueFactory<>("id")
        );


        col2.setCellValueFactory(
                new PropertyValueFactory<>("nombre")
        );


        col3.setCellValueFactory(
                new PropertyValueFactory<>("correo")
        );


        col4.setCellValueFactory(
                new PropertyValueFactory<>("activo")
        );



        tablaDatos.setItems(datos);



    }






    // ============================
    // MENU
    // ============================



    @FXML
    public void mostrarInicio(){


        moduloActual="Inicio";


        lblTitulo.setText(
                "Panel principal de administración"
        );


        datos.clear();



    }





    @FXML
    public void mostrarUsuarios(){


        moduloActual="Usuarios";


        lblTitulo.setText(
                "Gestión de Usuarios"
        );


        cargarUsuarios();



    }






    @FXML
    public void mostrarMedicos(){


        moduloActual="Medicos";


        lblTitulo.setText(
                "Gestión de Médicos"
        );


        cargarUsuarios();


    }






    @FXML
    public void mostrarPacientes(){


        moduloActual="Pacientes";


        lblTitulo.setText(
                "Gestión de Pacientes"
        );


        cargarUsuarios();



    }






    @FXML
    public void mostrarCitas(){


        moduloActual="Citas";


        lblTitulo.setText(
                "Gestión de Citas"
        );


        datos.clear();


    }





    private void cargarUsuarios(){


        datos.clear();



        datos.addAll(
                personaDAO.listarTodos()
        );



        tablaDatos.refresh();



    }
    // ============================
    // BUSCAR
    // ============================


    @FXML
    public void buscar(){


        String texto = txtBuscar.getText();



        if(texto.isEmpty()){

            cargarUsuarios();

            return;

        }



        datos.clear();



        datos.addAll(
                personaDAO.buscar(texto)
        );



        tablaDatos.refresh();


    }






    // ============================
    // NUEVO REGISTRO
    // ============================


    @FXML
    public void nuevo(){


        if(moduloActual.equals("Inicio")){


            mostrarMensaje(
                    "Seleccione Usuarios primero"
            );


            return;

        }



        crearRegistro();



    }







    private void crearRegistro(){



        Dialog<ButtonType> dialog =
                new Dialog<>();


        dialog.setTitle(
                "Nuevo Registro"
        );



        VBox formulario =
                new VBox(10);



        TextField nombre =
                new TextField();

        nombre.setPromptText(
                "Nombre"
        );



        TextField apellido =
                new TextField();

        apellido.setPromptText(
                "Apellido"
        );



        TextField correo =
                new TextField();

        correo.setPromptText(
                "Correo"
        );



        PasswordField contrasena =
                new PasswordField();

        contrasena.setPromptText(
                "Contraseña"
        );



        TextField telefono =
                new TextField();

        telefono.setPromptText(
                "Teléfono"
        );



        ComboBox<String> rol =
                new ComboBox<>();


        rol.getItems().addAll(

                "ADMINISTRADOR",
                "MEDICO",
                "PACIENTE"

        );


        rol.setValue(
                "PACIENTE"
        );



        TextField informacion =
                new TextField();


        informacion.setPromptText(

                "Especialidad (Médico) / Historial (Paciente)"

        );





        formulario.getChildren().addAll(

                nombre,
                apellido,
                correo,
                contrasena,
                telefono,
                rol,
                informacion

        );





        dialog.getDialogPane()
                .setContent(
                        formulario
                );




        dialog.getDialogPane()
                .getButtonTypes()
                .addAll(

                        ButtonType.OK,
                        ButtonType.CANCEL

                );





        Optional<ButtonType> resultado =

                dialog.showAndWait();





        if(resultado.isPresent()

                &&

                resultado.get()==ButtonType.OK){





            if(nombre.getText().isEmpty()

                    ||

                    apellido.getText().isEmpty()

                    ||

                    correo.getText().isEmpty()

                    ||

                    contrasena.getText().isEmpty()){



                mostrarMensaje(
                        "Complete los campos obligatorios"
                );


                return;


            }







            Persona persona;



            switch(rol.getValue()){






                case "ADMINISTRADOR":



                    persona =
                            new Administrador(

                                    0,

                                    nombre.getText(),

                                    apellido.getText(),

                                    correo.getText(),

                                    contrasena.getText(),

                                    "ADMINISTRADOR",

                                    telefono.getText(),

                                    true

                            );

                    break;







                case "MEDICO":



                    persona =
                            new Medico(

                                    0,

                                    nombre.getText(),

                                    apellido.getText(),

                                    correo.getText(),

                                    contrasena.getText(),

                                    "MEDICO",

                                    telefono.getText(),

                                    true,

                                    0,

                                    informacion.getText()

                            );



                    break;







                default:




                    persona =
                            new Paciente(

                                    0,

                                    nombre.getText(),

                                    apellido.getText(),

                                    correo.getText(),

                                    contrasena.getText(),

                                    "PACIENTE",

                                    telefono.getText(),

                                    true,

                                    informacion.getText()

                            );



                    break;


            }





            boolean creado =
                    personaDAO.crear(persona);






            if(creado){



                mostrarMensaje(

                        "Registro creado correctamente"

                );



                cargarUsuarios();



            }else{


                mostrarMensaje(

                        "Error al guardar registro"

                );


            }





        }



    }
    // ============================
    // EDITAR
    // ============================


    @FXML
    public void editar(){


        Object seleccionado =
                tablaDatos
                        .getSelectionModel()
                        .getSelectedItem();



        if(seleccionado == null){


            mostrarMensaje(
                    "Seleccione un registro primero"
            );


            return;

        }



        if(seleccionado instanceof Persona){


            Persona persona =
                    (Persona) seleccionado;



            boolean actualizado =
                    personaDAO.actualizar(persona);



            if(actualizado){


                mostrarMensaje(
                        "Registro actualizado correctamente"
                );


                cargarUsuarios();



            }else{


                mostrarMensaje(
                        "No se pudo actualizar"
                );


            }



        }



    }







    // ============================
    // ELIMINAR
    // ============================


    @FXML
    public void eliminar(){



        Object seleccionado =
                tablaDatos
                        .getSelectionModel()
                        .getSelectedItem();




        if(seleccionado == null){



            mostrarMensaje(
                    "Seleccione un registro"
            );


            return;

        }






        Persona persona =
                (Persona) seleccionado;





        Alert confirmar =
                new Alert(
                        Alert.AlertType.CONFIRMATION
                );



        confirmar.setTitle(
                "Eliminar"
        );



        confirmar.setHeaderText(
                null
        );



        confirmar.setContentText(

                "¿Desea eliminar este registro?"

        );





        Optional<ButtonType> respuesta =

                confirmar.showAndWait();






        if(respuesta.isPresent()

                &&

                respuesta.get()==ButtonType.OK){





            boolean eliminado =

                    personaDAO.eliminar(
                            persona.getId()
                    );






            if(eliminado){



                mostrarMensaje(

                        "Registro eliminado correctamente"

                );


                cargarUsuarios();



            }else{


                mostrarMensaje(

                        "No se pudo eliminar"

                );


            }





        }




    }







    // ============================
    // ACTUALIZAR TABLA
    // ============================


    @FXML
    public void actualizar(){


        cargarUsuarios();



        mostrarMensaje(

                "Datos actualizados"

        );


    }







    // ============================
    // CERRAR SESIÓN
    // ============================


    @FXML
    public void cerrarSesion(){


        Sesion.cerrarSesion();



        Stage ventana =

                (Stage)
                        btnCerrarSesion
                                .getScene()
                                .getWindow();



        ventana.close();


    }








    // ============================
    // MENSAJES
    // ============================


    private void mostrarMensaje(
            String mensaje
    ){



        Alert alerta =

                new Alert(
                        Alert.AlertType.INFORMATION
                );



        alerta.setTitle(
                "Sistema"
        );


        alerta.setHeaderText(
                null
        );


        alerta.setContentText(
                mensaje
        );



        alerta.showAndWait();



    }



}