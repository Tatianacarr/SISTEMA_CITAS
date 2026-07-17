package com.proyecto.sistema_citas_poli.Controller;


import com.proyecto.sistema_citas_poli.DAO.PersonaDAO;
import com.proyecto.sistema_citas_poli.DAO.PersonaDAOImpl;
import com.proyecto.sistema_citas_poli.Model.Persona;
import com.proyecto.sistema_citas_poli.Util.Sesion;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;


import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;


import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;


import javafx.stage.Stage;


import java.util.List;



public class AdministradorController {


    //==============================
    // FXML
    //==============================


    @FXML
    private Label lblAdministrador;


    @FXML
    private Label lblTitulo;


    @FXML
    private TextField txtBuscar;



    @FXML
    private TableView<Persona> tablaDatos;



    @FXML
    private TableColumn<Persona,Integer> colId;


    @FXML
    private TableColumn<Persona,String> colNombre;


    @FXML
    private TableColumn<Persona,String> colApellido;


    @FXML
    private TableColumn<Persona,String> colCorreo;


    @FXML
    private TableColumn<Persona,String> colRol;


    @FXML
    private TableColumn<Persona,String> colTelefono;


    @FXML
    private TableColumn<Persona,Boolean> colEstado;




    //==============================
    // DAO
    //==============================


    private final PersonaDAO personaDAO =
            new PersonaDAOImpl();



    private final ObservableList<Persona> lista =
            FXCollections.observableArrayList();



    private Persona personaSeleccionada;



    //==============================
    // INICIO
    //==============================


    @FXML
    public void initialize(){


        cargarAdministrador();


        configurarTabla();


        cargarUsuarios();



        tablaDatos.setOnMouseClicked(event -> {

            seleccionarUsuario();

        });


    }





    private void cargarAdministrador(){


        if(Sesion.existeSesion()){


            Persona admin =
                    Sesion.getPersona();


            lblAdministrador.setText(

                    admin.getNombre()
                            +" "
                            +admin.getApellido()

            );


        }


    }




    //==============================
    // TABLA
    //==============================


    private void configurarTabla(){


        colId.setCellValueFactory(
                new PropertyValueFactory<>("id")
        );


        colNombre.setCellValueFactory(
                new PropertyValueFactory<>("nombre")
        );


        colApellido.setCellValueFactory(
                new PropertyValueFactory<>("apellido")
        );


        colCorreo.setCellValueFactory(
                new PropertyValueFactory<>("correo")
        );


        colRol.setCellValueFactory(
                new PropertyValueFactory<>("rol")
        );


        colTelefono.setCellValueFactory(
                new PropertyValueFactory<>("telefono")
        );


        colEstado.setCellValueFactory(
                new PropertyValueFactory<>("activo")
        );


    }




    private void cargarUsuarios(){


        lista.clear();


        lista.addAll(
                personaDAO.listarTodos()
        );


        tablaDatos.setItems(lista);


    }





    //==============================
    // MENU
    //==============================


    @FXML
    public void mostrarInicio(){

        lblTitulo.setText(
                "Panel de Administración"
        );

        cargarUsuarios();

    }




    @FXML
    public void mostrarUsuarios(){

        lblTitulo.setText(
                "Usuarios Registrados"
        );

        cargarUsuarios();

    }





    //==============================
    // BUSCAR
    //==============================


    @FXML
    public void buscar(){


        String texto =
                txtBuscar.getText()
                        .trim();



        if(texto.isEmpty()){


            cargarUsuarios();

            return;

        }



        lista.clear();



        try{


            int id =
                    Integer.parseInt(texto);



            Persona persona =
                    personaDAO.buscarPorId(id);



            if(persona != null){

                lista.add(persona);

            }



        }catch(NumberFormatException e){



            List<Persona> resultado =
                    personaDAO.buscar(texto);



            lista.addAll(resultado);


        }



        tablaDatos.setItems(lista);



    }






    //==============================
    // SELECCIONAR
    //==============================


    private void seleccionarUsuario(){


        personaSeleccionada =

                tablaDatos
                        .getSelectionModel()
                        .getSelectedItem();


    }






    //==============================
    // NUEVO
    //==============================


    @FXML
    public void nuevo(){


        abrirRegistro(null);


    }






    //==============================
    // EDITAR
    //==============================


    @FXML
    public void editar(){


        if(personaSeleccionada == null){


            alerta(
                    "Seleccione un usuario para editar"
            );


            return;

        }



        abrirRegistro(
                personaSeleccionada
        );


    }





    private void abrirRegistro(Persona persona){


        try{


            FXMLLoader loader =
                    new FXMLLoader(

                            getClass()
                                    .getResource(
                                            "/com/proyecto/sistema_citas_poli/registro.fxml"
                                    )

                    );



            Parent root =
                    loader.load();



            RegistroController controller =
                    loader.getController();



            if(persona != null){


                controller.cargarUsuario(
                        persona
                );


            }




            Stage stage =
                    new Stage();



            stage.setTitle(
                    "Registro Usuario"
            );



            stage.setScene(
                    new Scene(root)
            );



            stage.show();



        }catch(Exception e){


            e.printStackTrace();


            alerta(
                    "Error abriendo registro"
            );


        }


    }







    //==============================
    // ACTUALIZAR
    //==============================


    @FXML
    public void actualizar(){


        cargarUsuarios();


        alerta(
                "Tabla actualizada"
        );


    }






    //==============================
    // ELIMINAR
    //==============================


    @FXML
    public void eliminar(){


        if(personaSeleccionada == null){


            alerta(
                    "Seleccione un usuario"
            );


            return;

        }




        boolean eliminado =

                personaDAO.eliminar(

                        personaSeleccionada.getId()

                );



        if(eliminado){


            alerta(
                    "Usuario eliminado correctamente"
            );


            cargarUsuarios();



        }else{


            alerta(
                    "No se pudo eliminar"
            );


        }


    }





    //==============================
    // FILTROS
    //==============================


    @FXML
    public void mostrarMedicos(){


        lista.clear();



        for(Persona p:
                personaDAO.listarTodos()){


            if(p.getRol()
                    .equalsIgnoreCase("MEDICO")){


                lista.add(p);


            }


        }


        tablaDatos.setItems(lista);


        lblTitulo.setText(
                "Médicos"
        );


    }





    @FXML
    public void mostrarPacientes(){


        lista.clear();



        for(Persona p:
                personaDAO.listarTodos()){


            if(p.getRol()
                    .equalsIgnoreCase("PACIENTE")){


                lista.add(p);


            }


        }


        tablaDatos.setItems(lista);


        lblTitulo.setText(
                "Pacientes"
        );


    }
    @FXML
    public void mostrarCitas(ActionEvent event){

        try{

            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource(
                            "/com/proyecto/sistema_citas_poli/cita.fxml"
                    )
            );


            Scene scene = new Scene(loader.load());


            Stage stage = (Stage)
                    ((Node) event.getSource())
                            .getScene()
                            .getWindow();


            stage.setScene(scene);
            stage.show();


        }catch(Exception e){

            e.printStackTrace();

            alerta(
                    "No se pudo abrir el módulo de citas"
            );

        }

    }


    //==============================
    // SESION
    //==============================


    @FXML
    public void cerrarSesion(){


        Sesion.cerrarSesion();


        alerta(
                "Sesión cerrada"
        );


    }






    private void alerta(String mensaje){


        Alert alert =
                new Alert(
                        Alert.AlertType.INFORMATION
                );


        alert.setTitle(
                "MediCitas POLI"
        );


        alert.setHeaderText(null);


        alert.setContentText(mensaje);


        alert.showAndWait();


    }



}