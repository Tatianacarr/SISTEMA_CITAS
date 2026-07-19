package com.proyecto.sistema_citas_poli.Controller;

import com.proyecto.sistema_citas_poli.DAO.PersonaDAO;
import com.proyecto.sistema_citas_poli.DAO.PersonaDAOImpl;
import com.proyecto.sistema_citas_poli.Model.Persona;
import com.proyecto.sistema_citas_poli.Util.Sesion;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {

    @FXML
    private TextField txtCorreo;

    @FXML
    private PasswordField txtContrasena;

    @FXML
    private Button btnIngresar;

    @FXML
    private Button btnRegistro;

    private final PersonaDAO personaDAO = new PersonaDAOImpl();

    @FXML
    public void ingresar() {

        String correo = txtCorreo.getText().trim();
        String password = txtContrasena.getText().trim();

        // Validaciones
        if (correo.isEmpty() || password.isEmpty()) {
            mostrarMensaje(
                    "Campos incompletos",
                    "Ingrese el correo y la contraseña."
            );
            return;
        }

        if (!correo.contains("@")) {
            mostrarMensaje(
                    "Correo inválido",
                    "Ingrese un correo electrónico válido."
            );
            return;
        }

        Persona persona = personaDAO.login(correo, password);

        if (persona == null) {
            mostrarMensaje(
                    "Acceso denegado",
                    "Correo o contraseña incorrectos."
            );
            return;
        }

        // Guardar sesión
        Sesion.iniciarSesion(persona);

        abrirVentanaSegunRol(persona);
    }

    private void abrirVentanaSegunRol(Persona persona) {

        String ventana;

        switch (persona.getRol().toUpperCase()) {

            case "ADMINISTRADOR":
                ventana = "/com/proyecto/sistema_citas_poli/administrador.fxml";
                break;

            case "MEDICO":
                ventana = "/com/proyecto/sistema_citas_poli/medico.fxml";
                break;

            case "PACIENTE":
                ventana = "/com/proyecto/sistema_citas_poli/paciente.fxml";
                break;

            default:
                mostrarMensaje(
                        "Error",
                        "El usuario no tiene un rol válido."
                );
                return;
        }

        try {

            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource(ventana)
            );

            Scene scene = new Scene(loader.load());

            Stage stage = (Stage) btnIngresar.getScene().getWindow();

            stage.setScene(scene);
            stage.setTitle("MediCitas POLI - " + persona.getRol());
            stage.centerOnScreen();
            stage.show();

        } catch (Exception e) {

            e.printStackTrace();

            mostrarMensaje(
                    "Error",
                    "No se pudo abrir la ventana correspondiente al rol."
            );
        }
    }

    @FXML
    public void abrirRegistro() {

        try {

            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource(
                            "/com/proyecto/sistema_citas_poli/registro.fxml"
                    )
            );

            Scene scene = new Scene(loader.load());

            Stage stage = (Stage) btnRegistro.getScene().getWindow();

            stage.setScene(scene);
            stage.setTitle("Registro");
            stage.centerOnScreen();

        } catch (Exception e) {

            e.printStackTrace();

            mostrarMensaje(
                    "Error",
                    "No se pudo abrir la ventana de registro."
            );
        }
    }

    private void mostrarMensaje(String titulo, String mensaje) {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);

        alert.showAndWait();
    }
}