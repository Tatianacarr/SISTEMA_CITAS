package com.proyecto.sistema_citas_poli.Controller;


import com.proyecto.sistema_citas_poli.DAO.*;
import com.proyecto.sistema_citas_poli.Model.*;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.sql.Date;
import java.sql.Time;
import java.util.Optional;



public class AdministradorController {


    //==============================
    // PANELES
    //==============================

    @FXML
    private VBox panelInicio;

    @FXML
    private VBox panelUsuarios;

    @FXML
    private VBox panelMedicos;

    @FXML
    private VBox panelCitas;


    //==============================
    // INFORMACION INICIO
    //==============================


    @FXML
    private Label lblAdministrador;


    @FXML
    private Label lblTotalPacientes;


    @FXML
    private Label lblTotalMedicos;


    @FXML
    private Label lblTotalCitas;


    //==============================
    // PACIENTES
    //==============================


    @FXML
    private TableView<Paciente> tablaPacientes;


    @FXML
    private TableColumn<Paciente, Integer> colPacienteId;


    @FXML
    private TableColumn<Paciente, String> colPacienteNombre;


    @FXML
    private TableColumn<Paciente, String> colPacienteApellido;


    @FXML
    private TableColumn<Paciente, String> colPacienteCorreo;


    @FXML
    private TableColumn<Paciente, String> colPacienteTelefono;


    @FXML
    private TextField txtBuscarPaciente;


    //==============================
    // MEDICOS
    //==============================


    @FXML
    private TableView<Medico> tablaMedicos;


    @FXML
    private TableColumn<Medico, Integer> colMedicoId;


    @FXML
    private TableColumn<Medico, String> colMedicoNombre;


    @FXML
    private TableColumn<Medico, String> colMedicoApellido;


    @FXML
    private TableColumn<Medico, String> colMedicoEspecialidad;


    @FXML
    private TableColumn<Medico, String> colMedicoCorreo;


    @FXML
    private TableColumn<Medico, String> colMedicoTelefono;


    @FXML
    private TextField txtBuscarMedico;


    //==============================
    // CITAS
    //==============================


    @FXML
    private TableView<Cita> tablaCitas;


    @FXML
    private TableColumn<Cita, Integer> colCitaId;


    @FXML
    private TableColumn<Cita, String> colCitaPaciente;


    @FXML
    private TableColumn<Cita, String> colCitaMedico;


    @FXML
    private TableColumn<Cita, String> colCitaEspecialidad;


    @FXML
    private TableColumn<Cita, Date> colCitaFecha;


    @FXML
    private TableColumn<Cita, Time> colCitaHora;


    @FXML
    private TableColumn<Cita, String> colCitaEstado;


    @FXML
    private TableColumn<Cita, String> colCitaObservacion;


    //==============================
    // DAO
    //==============================


    private final PacienteDAO pacienteDAO =
            new PacienteDAOImpl();


    private final MedicoDAO medicoDAO =
            new MedicoDAOImpl();


    private final CitaDAO citaDAO =
            new CitaDAOImpl();


    //==============================
    // LISTAS
    //==============================


    private ObservableList<Paciente> pacientes =
            FXCollections.observableArrayList();


    private ObservableList<Medico> medicos =
            FXCollections.observableArrayList();


    private ObservableList<Cita> citas =
            FXCollections.observableArrayList();


    //==============================
    // SELECCIONADOS
    //==============================


    private Paciente pacienteSeleccionado;


    private Medico medicoSeleccionado;


    private Cita citaSeleccionada;


    //==============================
    // INICIO
    //==============================


    @FXML
    public void initialize() {


        configurarTablaPacientes();

        configurarTablaMedicos();

        configurarTablaCitas();


        mostrarInicio(null);


    }


    @FXML
    private void mostrarInicio(ActionEvent event) {


        panelInicio.setVisible(true);

        panelUsuarios.setVisible(false);

        panelMedicos.setVisible(false);

        panelCitas.setVisible(false);


        cargarDatosInicio();

    }


    private void cargarDatosInicio() {


        try {


            lblTotalPacientes.setText(
                    "👥 Pacientes: "
                            + pacienteDAO.listarTodos().size()
            );


            lblTotalMedicos.setText(
                    "👨‍⚕️ Médicos: "
                            + medicoDAO.leerTodos().size()
            );


            lblTotalCitas.setText(
                    "📅 Citas: "
                            + citaDAO.listarTodas().size()
            );


        } catch (Exception e) {


            System.out.println(
                    "Error inicio: "
                            + e.getMessage()
            );


        }


    }
    //==============================
    // PACIENTES
    //==============================


    @FXML
    private void mostrarUsuarios(ActionEvent event) {


        panelInicio.setVisible(false);

        panelUsuarios.setVisible(true);

        panelMedicos.setVisible(false);

        panelCitas.setVisible(false);


        cargarPacientes();

    }


    private void cargarPacientes() {


        pacientes =
                FXCollections.observableArrayList(
                        pacienteDAO.listarTodos()
                );


        tablaPacientes.setItems(pacientes);


    }


    @FXML
    private void buscarPaciente(ActionEvent event) {


        String texto =
                txtBuscarPaciente.getText()
                        .trim()
                        .toLowerCase();


        if (texto.isEmpty()) {


            cargarPacientes();

            return;

        }


        ObservableList<Paciente> resultado =
                FXCollections.observableArrayList();


        for (Paciente p : pacienteDAO.listarTodos()) {


            if (
                    p.getNombre()
                            .toLowerCase()
                            .contains(texto)

                            ||

                            p.getApellido()
                                    .toLowerCase()
                                    .contains(texto)

                            ||

                            p.getCorreo()
                                    .toLowerCase()
                                    .contains(texto)

            ) {


                resultado.add(p);

            }


        }


        tablaPacientes.setItems(resultado);


    }


    @FXML
    private void seleccionarPaciente(MouseEvent event) {


        pacienteSeleccionado =
                tablaPacientes
                        .getSelectionModel()
                        .getSelectedItem();


    }


    //==============================
    // NUEVO PACIENTE
    //==============================


    @FXML
    private void nuevoPaciente(ActionEvent event) {


        TextInputDialog nombre =
                new TextInputDialog();


        nombre.setTitle("Nuevo paciente");

        nombre.setHeaderText("Ingrese nombre");


        Optional<String> n =
                nombre.showAndWait();


        if (n.isEmpty())
            return;


        TextInputDialog apellido =
                new TextInputDialog();


        apellido.setTitle("Nuevo paciente");

        apellido.setHeaderText("Ingrese apellido");


        Optional<String> a =
                apellido.showAndWait();


        if (a.isEmpty())
            return;


        TextInputDialog correo =
                new TextInputDialog();


        correo.setTitle("Nuevo paciente");

        correo.setHeaderText("Ingrese correo");


        Optional<String> c =
                correo.showAndWait();


        if (c.isEmpty())
            return;


        TextInputDialog telefono =
                new TextInputDialog();


        telefono.setTitle("Nuevo paciente");

        telefono.setHeaderText("Ingrese teléfono");


        Optional<String> t =
                telefono.showAndWait();


        if (t.isEmpty())
            return;


        Paciente paciente =
                new Paciente(

                        0,

                        n.get(),

                        a.get(),

                        c.get(),

                        "123456",

                        "PACIENTE",

                        t.get(),

                        true,

                        "Sin historial"

                );


        if (pacienteDAO.crear(paciente)) {


            mostrarMensaje(
                    "Paciente creado correctamente"
            );


            cargarPacientes();

            cargarDatosInicio();


        } else {


            mostrarMensaje(
                    "Error al crear paciente"
            );


        }


    }


    //==============================
    // EDITAR PACIENTE
    //==============================


    @FXML
    private void editarPaciente(ActionEvent event){

        pacienteSeleccionado =
                tablaPacientes.getSelectionModel().getSelectedItem();


        if(pacienteSeleccionado == null){

            mostrarMensaje(
                    "Seleccione un paciente primero"
            );

            return;
        }


        Dialog<Paciente> dialog = new Dialog<>();

        dialog.setTitle("Editar paciente");


        ButtonType guardar =
                new ButtonType(
                        "Guardar",
                        ButtonBar.ButtonData.OK_DONE
                );


        dialog.getDialogPane()
                .getButtonTypes()
                .addAll(
                        guardar,
                        ButtonType.CANCEL
                );



        TextField nombre =
                new TextField(
                        pacienteSeleccionado.getNombre()
                );


        TextField apellido =
                new TextField(
                        pacienteSeleccionado.getApellido()
                );


        TextField correo =
                new TextField(
                        pacienteSeleccionado.getCorreo()
                );


        TextField telefono =
                new TextField(
                        pacienteSeleccionado.getTelefono()
                );


        VBox contenido =
                new VBox(
                        10,
                        new Label("Nombre"),
                        nombre,
                        new Label("Apellido"),
                        apellido,
                        new Label("Correo"),
                        correo,
                        new Label("Teléfono"),
                        telefono
                );


        contenido.setPadding(
                new Insets(20)
        );


        dialog.getDialogPane()
                .setContent(contenido);



        dialog.setResultConverter(boton -> {


            if(boton == guardar){


                pacienteSeleccionado.setNombre(
                        nombre.getText()
                );


                pacienteSeleccionado.setApellido(
                        apellido.getText()
                );


                pacienteSeleccionado.setCorreo(
                        correo.getText()
                );


                pacienteSeleccionado.setTelefono(
                        telefono.getText()
                );


                return pacienteSeleccionado;

            }


            return null;

        });



        dialog.showAndWait()
                .ifPresent(p -> {


                    if(pacienteDAO.actualizar(p)){


                        mostrarMensaje(
                                "Paciente actualizado correctamente"
                        );


                        cargarPacientes();


                    }else{


                        mostrarMensaje(
                                "Error al actualizar paciente"
                        );


                    }


                });


    }


    @FXML
    private void eliminarPaciente(ActionEvent event) {


        if (pacienteSeleccionado == null) {


            mostrarMensaje(
                    "Seleccione un paciente"
            );


            return;

        }


        Alert alerta =
                new Alert(Alert.AlertType.CONFIRMATION);


        alerta.setContentText(
                "¿Eliminar paciente?"
        );


        Optional<ButtonType> respuesta =
                alerta.showAndWait();


        if (respuesta.isPresent()
                &&
                respuesta.get() == ButtonType.OK) {


            if (pacienteDAO.eliminar(
                    pacienteSeleccionado.getId()
            )) {


                mostrarMensaje(
                        "Paciente eliminado"
                );


                cargarPacientes();

                cargarDatosInicio();


            }


        }


    }


    @FXML
    private void actualizarPacientes(ActionEvent event) {


        cargarPacientes();


        mostrarMensaje(
                "Pacientes actualizados"
        );


    }
    //==============================
    // MEDICOS
    //==============================


    @FXML
    private void mostrarMedicos(ActionEvent event) {


        panelInicio.setVisible(false);

        panelUsuarios.setVisible(false);

        panelMedicos.setVisible(true);

        panelCitas.setVisible(false);


        cargarMedicos();


    }


    private void cargarMedicos() {


        medicos =
                FXCollections.observableArrayList(
                        medicoDAO.leerTodos()
                );


        tablaMedicos.setItems(medicos);


    }


    @FXML
    private void buscarMedico(ActionEvent event) {


        String texto =
                txtBuscarMedico.getText()
                        .trim()
                        .toLowerCase();


        if (texto.isEmpty()) {


            cargarMedicos();

            return;

        }


        ObservableList<Medico> resultado =
                FXCollections.observableArrayList();


        for (Medico m : medicoDAO.leerTodos()) {


            if (
                    m.getNombre()
                            .toLowerCase()
                            .contains(texto)

                            ||

                            m.getApellido()
                                    .toLowerCase()
                                    .contains(texto)

                            ||

                            m.getEspecialidad()
                                    .toLowerCase()
                                    .contains(texto)

            ) {


                resultado.add(m);

            }


        }


        tablaMedicos.setItems(resultado);


    }


    @FXML
    private void seleccionarMedico(MouseEvent event) {


        medicoSeleccionado =
                tablaMedicos
                        .getSelectionModel()
                        .getSelectedItem();


    }


    //==============================
    // NUEVO MEDICO
    //==============================


    @FXML
    private void nuevoMedico(ActionEvent event) {


        TextInputDialog nombre =
                new TextInputDialog();


        nombre.setTitle("Nuevo médico");

        nombre.setHeaderText("Nombre");


        Optional<String> n =
                nombre.showAndWait();


        if (n.isEmpty())
            return;


        TextInputDialog apellido =
                new TextInputDialog();


        apellido.setTitle("Nuevo médico");

        apellido.setHeaderText("Apellido");


        Optional<String> a =
                apellido.showAndWait();


        if (a.isEmpty())
            return;


        TextInputDialog correo =
                new TextInputDialog();


        correo.setTitle("Nuevo médico");

        correo.setHeaderText("Correo");


        Optional<String> c =
                correo.showAndWait();


        if (c.isEmpty())
            return;


        TextInputDialog especialidad =
                new TextInputDialog();


        especialidad.setTitle("Nuevo médico");

        especialidad.setHeaderText("Especialidad");


        Optional<String> e =
                especialidad.showAndWait();


        if (e.isEmpty())
            return;


        TextInputDialog telefono =
                new TextInputDialog();


        telefono.setTitle("Nuevo médico");

        telefono.setHeaderText("Teléfono");


        Optional<String> t =
                telefono.showAndWait();


        if (t.isEmpty())
            return;


        Medico medico =
                new Medico(

                        0,

                        n.get(),

                        a.get(),

                        c.get(),

                        "123456",

                        "MEDICO",

                        t.get(),

                        true,

                        0,

                        e.get()

                );


        if (medicoDAO.crear(medico)) {


            mostrarMensaje(
                    "Médico creado correctamente"
            );


            cargarMedicos();

            cargarDatosInicio();


        } else {


            mostrarMensaje(
                    "Error creando médico"
            );


        }


    }


    //==============================
    // EDITAR MEDICO
    //==============================

    @FXML
    private void editarMedico(ActionEvent event){


        medicoSeleccionado =
                tablaMedicos.getSelectionModel()
                        .getSelectedItem();



        if(medicoSeleccionado == null){


            mostrarMensaje(
                    "Seleccione un médico primero"
            );

            return;

        }



        Dialog<Medico> dialog =
                new Dialog<>();


        dialog.setTitle(
                "Editar médico"
        );


        ButtonType guardar =
                new ButtonType(
                        "Guardar",
                        ButtonBar.ButtonData.OK_DONE
                );



        dialog.getDialogPane()
                .getButtonTypes()
                .addAll(
                        guardar,
                        ButtonType.CANCEL
                );




        TextField nombre =
                new TextField(
                        medicoSeleccionado.getNombre()
                );


        TextField apellido =
                new TextField(
                        medicoSeleccionado.getApellido()
                );


        TextField correo =
                new TextField(
                        medicoSeleccionado.getCorreo()
                );


        TextField telefono =
                new TextField(
                        medicoSeleccionado.getTelefono()
                );


        TextField especialidad =
                new TextField(
                        medicoSeleccionado.getEspecialidad()
                );




        VBox contenido =
                new VBox(
                        10,

                        new Label("Nombre"),
                        nombre,

                        new Label("Apellido"),
                        apellido,

                        new Label("Correo"),
                        correo,

                        new Label("Teléfono"),
                        telefono,

                        new Label("Especialidad"),
                        especialidad
                );


        contenido.setPadding(
                new Insets(20)
        );



        dialog.getDialogPane()
                .setContent(contenido);




        dialog.setResultConverter(boton -> {



            if(boton == guardar){


                medicoSeleccionado.setNombre(
                        nombre.getText()
                );


                medicoSeleccionado.setApellido(
                        apellido.getText()
                );


                medicoSeleccionado.setCorreo(
                        correo.getText()
                );


                medicoSeleccionado.setTelefono(
                        telefono.getText()
                );


                medicoSeleccionado.setEspecialidad(
                        especialidad.getText()
                );



                return medicoSeleccionado;

            }


            return null;

        });



        dialog.showAndWait()
                .ifPresent(m -> {


                    if(medicoDAO.actualizar(m)){


                        mostrarMensaje(
                                "Médico actualizado correctamente"
                        );


                        cargarMedicos();


                    }else{


                        mostrarMensaje(
                                "Error al actualizar médico"
                        );


                    }


                });


    }

    @FXML
    private void eliminarMedico(ActionEvent event) {


        if (medicoSeleccionado == null) {


            mostrarMensaje(
                    "Seleccione un médico"
            );


            return;

        }


        if (medicoDAO.eliminar(
                medicoSeleccionado.getMedicoId()
        )) {


            mostrarMensaje(
                    "Médico eliminado"
            );


            cargarMedicos();

            cargarDatosInicio();


        }


    }


    @FXML
    private void actualizarMedicos(ActionEvent event) {


        cargarMedicos();


        mostrarMensaje(
                "Médicos actualizados"
        );


    }


    //==============================
    // CITAS
    //==============================


    @FXML
    private void mostrarCitas(ActionEvent event) {


        panelInicio.setVisible(false);

        panelUsuarios.setVisible(false);

        panelMedicos.setVisible(false);

        panelCitas.setVisible(true);


        cargarCitas();


    }


    private void cargarCitas() {


        citas =
                FXCollections.observableArrayList(
                        citaDAO.listarTodas()
                );


        tablaCitas.setItems(citas);


    }


    //==============================
    // CONFIGURAR TABLAS
    //==============================


    private void configurarTablaPacientes() {


        colPacienteId.setCellValueFactory(
                data ->
                        new SimpleIntegerProperty(
                                data.getValue().getId()
                        ).asObject()
        );


        colPacienteNombre.setCellValueFactory(
                data ->
                        new SimpleStringProperty(
                                data.getValue().getNombre()
                        )
        );


        colPacienteApellido.setCellValueFactory(
                data ->
                        new SimpleStringProperty(
                                data.getValue().getApellido()
                        )
        );


        colPacienteCorreo.setCellValueFactory(
                data ->
                        new SimpleStringProperty(
                                data.getValue().getCorreo()
                        )
        );


        colPacienteTelefono.setCellValueFactory(
                data ->
                        new SimpleStringProperty(
                                data.getValue().getTelefono()
                        )
        );


    }
    private void configurarTablaMedicos(){


        colMedicoId.setCellValueFactory(
                data ->
                        new SimpleIntegerProperty(
                                data.getValue().getId()
                        ).asObject()
        );


        colMedicoNombre.setCellValueFactory(
                data ->
                        new SimpleStringProperty(
                                data.getValue().getNombre()
                        )
        );


        colMedicoApellido.setCellValueFactory(
                data ->
                        new SimpleStringProperty(
                                data.getValue().getApellido()
                        )
        );


        colMedicoEspecialidad.setCellValueFactory(
                data ->
                        new SimpleStringProperty(
                                data.getValue().getEspecialidad()
                        )
        );


        colMedicoCorreo.setCellValueFactory(
                data ->
                        new SimpleStringProperty(
                                data.getValue().getCorreo()
                        )
        );


        colMedicoTelefono.setCellValueFactory(
                data ->
                        new SimpleStringProperty(
                                data.getValue().getTelefono()
                        )
        );


    }







    private void configurarTablaCitas(){



        colCitaId.setCellValueFactory(
                data ->
                        new SimpleIntegerProperty(
                                data.getValue().getId()
                        ).asObject()
        );



        colCitaPaciente.setCellValueFactory(
                data ->
                        new SimpleStringProperty(
                                data.getValue().getPaciente()
                        )
        );



        colCitaMedico.setCellValueFactory(
                data ->
                        new SimpleStringProperty(
                                data.getValue().getMedico()
                        )
        );



        colCitaEspecialidad.setCellValueFactory(
                data ->
                        new SimpleStringProperty(
                                data.getValue().getEspecialidad()
                        )
        );



        colCitaFecha.setCellValueFactory(
                data ->
                        new SimpleObjectProperty<>(
                                data.getValue().getFecha()
                        )
        );



        colCitaHora.setCellValueFactory(
                data ->
                        new SimpleObjectProperty<>(
                                data.getValue().getHora()
                        )
        );



        colCitaEstado.setCellValueFactory(
                data ->
                        new SimpleStringProperty(
                                data.getValue().getEstado()
                        )
        );



        colCitaObservacion.setCellValueFactory(
                data ->
                        new SimpleStringProperty(
                                data.getValue().getObservacion()
                        )
        );


    }








    //==============================
    // CERRAR SESION
    //==============================


    @FXML
    private void cerrarSesion(ActionEvent event){


        mostrarMensaje(
                "Sesión cerrada correctamente"
        );


    }








    //==============================
    // MENSAJES
    //==============================


    private void mostrarMensaje(String mensaje){


        Alert alerta =
                new Alert(
                        Alert.AlertType.INFORMATION
                );


        alerta.setTitle(
                "MediCitas POLI"
        );


        alerta.setHeaderText(null);


        alerta.setContentText(
                mensaje
        );


        alerta.showAndWait();


    }



}