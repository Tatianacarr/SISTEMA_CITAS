module com.proyecto.sistema_citas_poli {

    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;

    opens com.proyecto.sistema_citas_poli.Controller
            to javafx.fxml;

    opens com.proyecto.sistema_citas_poli.Model
            to javafx.base;

    exports com.proyecto.sistema_citas_poli.App;

}